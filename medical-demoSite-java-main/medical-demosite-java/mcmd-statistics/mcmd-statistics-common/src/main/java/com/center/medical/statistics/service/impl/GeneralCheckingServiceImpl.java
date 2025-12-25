package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.poi.ExcelExp;
import com.center.medical.common.utils.poi.ExcelUtilManySheet;
import com.center.medical.statistics.bean.param.GeneralCheckingParam;
import com.center.medical.statistics.bean.vo.AnalyseTotalVo;
import com.center.medical.statistics.bean.vo.GeneralCheckingVo;
import com.center.medical.statistics.dao.GeneralCheckingMapper;
import com.center.medical.statistics.service.GeneralCheckingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 总检工作量(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-19 18:07:47
 */
@Slf4j
@Service("generalCheckingService")
@RequiredArgsConstructor
public class GeneralCheckingServiceImpl extends ServiceImpl<GeneralCheckingMapper, Peispatient> implements GeneralCheckingService {

private final GeneralCheckingMapper generalCheckingMapper;

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param Peispatient查询参数
    * @return 分页数据
    */
    @Override
    public IPage<GeneralCheckingVo> getList(PageParam<GeneralCheckingVo> page, GeneralCheckingParam param) {
        IPage<GeneralCheckingVo> iPage = null;
        //是否汇总
        param.setSfhz(0);
        if (ObjectUtils.isNotEmpty(param.getTjlx())) {
            if (param.getTjlx() == 0){
                //健康
                iPage = generalCheckingMapper.getList(page, param);
            }else if (param.getTjlx() == 1){
                //职业
                iPage = generalCheckingMapper.getList2(page, param);
            }
        }

        if (ObjectUtils.isNotEmpty(iPage) && ObjectUtils.isNotEmpty(iPage.getRecords())){
            //设置属性
            Calendar c = Calendar.getInstance();
            List<GeneralCheckingVo> list = iPage.getRecords();
            for (GeneralCheckingVo vo : list) {
                //工作量个人
                vo.setPersonnel(vo.getTotal() - vo.getOrg());
                if (ObjectUtils.isNotEmpty(vo.getExamTime())){
                    //星期
                    c.setTime(vo.getExamTime());
                    int dayForWeek = 0;
                    if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                        dayForWeek = 7;
                    } else {
                        dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                    }
                    vo.setDayForWeek(dayForWeek);
                }
            }
            iPage.setRecords(list);
        }

        return iPage;
    }

    /**
     * 总检医生工作量统计汇总
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalyseTotalVo> analyseTotal(PageParam<AnalyseTotalVo> page, GeneralCheckingParam param) {
        IPage<AnalyseTotalVo> iPage = null;
        //是否汇总
        param.setSfhz(1);
        if (ObjectUtils.isNotEmpty(param.getTjlx())) {
            if (param.getTjlx() == 0){
                //健康
                iPage = generalCheckingMapper.getTotalList(page, param);
            }else if (param.getTjlx() == 1){
                //职业
                iPage = generalCheckingMapper.getTotalList2(page, param);
            }
        }

        if (ObjectUtils.isNotEmpty(iPage) && ObjectUtils.isNotEmpty(iPage.getRecords())){
            //设置属性
            List<AnalyseTotalVo> list = iPage.getRecords();
            for (AnalyseTotalVo vo : list) {
                //工作量个人
                vo.setPersonnel(vo.getTotal() - vo.getOrg());
            }
            iPage.setRecords(list);
        }

        return iPage;
    }


    /**
     * 导出Excel
     * @param response
     * @param param
     */
    @Override
    public void getExportData(HttpServletResponse response, GeneralCheckingParam param) {
        PageParam<GeneralCheckingVo> page = new PageParam<>();
        page.setSize(99999);
        IPage<GeneralCheckingVo> iPage1 = getList(page, param);
        List<GeneralCheckingVo> records1 = iPage1.getRecords();
        for (int i = 0; i < records1.size(); i++) {
            GeneralCheckingVo vo = records1.get(i);
            vo.setRownum(i+1);
        }

        PageParam<AnalyseTotalVo> page2 = new PageParam<>();
        page2.setSize(99999);
        IPage<AnalyseTotalVo> iPage2 = analyseTotal(page2, param);
        List<AnalyseTotalVo> records2 = iPage2.getRecords();
        for (int i = 0; i < records2.size(); i++) {
            AnalyseTotalVo vo = records2.get(i);
            vo.setRownum(i+1);
        }
        //导出
        ExcelExp e1 = new ExcelExp("工作量明细", records1, GeneralCheckingVo.class);
        ExcelExp e2 = new ExcelExp("工作量汇总", records2, AnalyseTotalVo.class);
        List<ExcelExp> mysheet = new ArrayList<ExcelExp>();
        mysheet.add(e1);
        mysheet.add(e2);
        ExcelUtilManySheet<List<ExcelExp>> util2 = new ExcelUtilManySheet<List<ExcelExp>>(mysheet);
        try {
            util2.exportExcelManySheet(response, mysheet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

