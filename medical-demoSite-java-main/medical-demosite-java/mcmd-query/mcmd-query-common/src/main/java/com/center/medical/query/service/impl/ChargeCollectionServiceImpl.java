package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.CCPageParam;
import com.center.medical.query.bean.vo.CCPageVo;
import com.center.medical.query.dao.ChargeCollectionMapper;
import com.center.medical.query.service.ChargeCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 自费收费汇总(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("chargeCollectionService")
@RequiredArgsConstructor
public class ChargeCollectionServiceImpl extends ServiceImpl<ChargeCollectionMapper, Peispatient> implements ChargeCollectionService {

    private final ChargeCollectionMapper chargeCollectionMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CCPageVo> getList(PageParam<CCPageVo> page, CCPageParam param) {
        return chargeCollectionMapper.getList(page, param);
    }


    /**
     * 导出自费收费汇总
     *
     * @param param
     * @return
     */
    @Override
    public void getExportData(HttpServletResponse response, CCPageParam param) {

        List<CCPageVo> list1 = chargeCollectionMapper.getExportData(param);
        //排序 数据库seq都是空的，暂时注释掉
//        exportData.sort(Comparator.comparingInt(CCPageVo::getSeq));
        //去重
        Set<String> uniqueNames = new HashSet<>();
        List<String> resultList = new ArrayList<>();
        for (CCPageVo entity : list1) {
            if (uniqueNames.add(entity.getPayway())) {
                resultList.add(entity.getPayway());
            }
        }
        //查询导出数据-总计
        List<Map<String, String>> list2 = chargeCollectionMapper.getTotalCollectionSql(param,resultList);


        //导出多sheet
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet1 = workbook.createSheet("自费收费汇总");
            Sheet sheet2 = workbook.createSheet("自费收费汇总统计");

            // 创建具有边框和居中对齐的单元格样式
            CellStyle style = workbook.createCellStyle();
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setAlignment(HorizontalAlignment.CENTER);

            // 在Sheet1中创建行和单元格，并写入数据
            Row row1 = sheet1.createRow(0);
            Cell cell11 = row1.createCell(0);
            cell11.setCellValue("序号");
            cell11.setCellStyle(style);

            Cell cell12 = row1.createCell(1);
            cell12.setCellValue("收费方式");
            cell12.setCellStyle(style);

            Cell cell13 = row1.createCell(2);
            cell13.setCellValue("收费员");
            cell13.setCellStyle(style);

            Cell cell14 = row1.createCell(3);
            cell14.setCellValue("金额");
            cell14.setCellStyle(style);

            for (int i = 0; i < list1.size(); i++) {
                CCPageVo vo = list1.get(i);
                Row row2 = sheet1.createRow(i+1);
                //序号
                Cell cell1 = row2.createCell(0);
                cell1.setCellValue(i+1);
                cell1.setCellStyle(style);
                //收费方式
                Cell cell2 = row2.createCell(1);
                cell2.setCellValue(vo.getPayway());
                cell2.setCellStyle(style);
                //收费员
                Cell cell3 = row2.createCell(2);
                cell3.setCellValue(vo.getFeecharger());
                cell3.setCellStyle(style);
                //金额
                Cell cell4 = row2.createCell(3);
                cell4.setCellValue(vo.getMoneyamountpaid());
                cell4.setCellStyle(style);
            }

            // 在Sheet2中创建行和单元格，并写入数据
            //标题
            Row row2 = sheet2.createRow(0);
            resultList.add(0,"收费员");
            resultList.add("总计");
            for (int i = 0; i < resultList.size(); i++) {
                Cell cell1 = row2.createCell(i);
                cell1.setCellValue(resultList.get(i));
                cell1.setCellStyle(style);
            }
            //内容
            for (int i = 0; i < list2.size(); i++) {
                Map<String, String> map = list2.get(i);
                int i1 = 0;
                Row row3 = sheet2.createRow(i+1);
                for (String key : resultList) {
                    Cell cell3 = row3.createCell(i1);
                    cell3.setCellValue(String.valueOf(map.get(key)));
                    cell3.setCellStyle(style);
                    i1++;
                }
            }
            // 自动调整列宽以适应内容
            sheet1.autoSizeColumn(0);
            sheet2.autoSizeColumn(0);
            // 设置响应头信息
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"自费收费汇总.xlsx\"");
            // 将Excel数据写入响应输出流
            workbook.write(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

