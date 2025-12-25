package com.center.medical.report.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.bean.model.PrintNotice;
import com.center.medical.report.bean.param.PrintNoticeParam;
import com.center.medical.report.bean.vo.PrintNoticeVo;
import com.center.medical.report.dao.CommentsProgessionalMapper;
import com.center.medical.report.dao.PrintNoticeMapper;
import com.center.medical.report.service.PrintNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("printNoticeService")
@RequiredArgsConstructor
public class PrintNoticeServiceImpl extends ServiceImpl<PrintNoticeMapper, PrintNotice> implements PrintNoticeService {

    private final PrintNoticeMapper printNoticeMapper;
    private final PeispatientMapper peispatientMapper;
    private final CommentsProgessionalMapper commentsProgessionalMapper;

    /**
     * 分页查询职业结果告知书
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PrintNoticeVo> getPrintNoticePage(PageParam<PrintNoticeVo> page, PrintNoticeParam param) {
        //根据打印类型设置总结分类ID,打印类型1：复查；2：职业禁忌证；3：可疑职业病
        String idPatientclass = param.getIdPatientclass();
        String serialNo = null;
        if("1".equals(idPatientclass)){
            serialNo="3";
        }else if("2".equals(idPatientclass)){
            serialNo="2";
        }else if("3".equals(idPatientclass)){
            serialNo="1";
        }else{
            serialNo="3";
        }

        param.setSerialNo(serialNo);
        //设置开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime dateTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(dateTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime end = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(end);
        }

        return printNoticeMapper.getPrintNoticePage(page,param);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @Override
    public PrintNotice getInfoById(String id) {
        return printNoticeMapper.getInfoById(id);
    }


    /**
     * 打印
     * @param param
     * @return
     */
    @Override
    public boolean print(PrintNoticeParam param) {
        try {
            List<String> patientcodes = param.getRowsId();
            String patientcode = "";
            if (patientcodes != null && patientcodes.size()>0) {
                for (int i=0;i<patientcodes.size();i++) {
                    String str = patientcodes.get(i).replaceAll(" ", "");
                    if (i==0) {
                        patientcode = "'"+str+"'";
                    }else{
                        patientcode = patientcode+",'"+str+"'";
                    }
                }
            } else {
                return false;
            }
            List<String> pdfUrls = getAllPdfUrl(patientcode,param.getIdPatientclass());

            if (ObjectUtils.isNotEmpty(pdfUrls) && pdfUrls.size()>0) {
                for (String p :pdfUrls) {
                    p = p.substring(0, p.indexOf(".")+1)+"docx";
                    // TODO: 2022/12/19 打印未完成
                    //PrintPdf.printExcel(p, Integer.valueOf(param.getAmount().replaceAll(" ", "")));
                }
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询所有符合条件的体检者所对应的PDF路径并返回
     * @param patientcode
     * @param idPatientclass
     * @return
     */
    public List<String> getAllPdfUrl(String patientcode, String idPatientclass) {
        // 打印类型1：复查；2：职业禁忌证；3：可疑职业病
        List<String> list = printNoticeMapper.getAllPdfUrl(idPatientclass,patientcode);

        List<String> pdfUrl = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            String path = FileTypePath.REAL_PATH;
            for (int i=0;i<list.size();i++) {
                String str = String.valueOf(list.get(i));
                pdfUrl.add(path+str);
            }
        }

        return pdfUrl;
    }



    /**
     * 获取所有体检者信息
     * @param patientcodes
     * @param page
     * @return
     */
    @Override
    public List<Peispatient> findAllPeispatientByPatientcode(List<String> patientcodes, PageParam<Peispatient> page) {
        QueryWrapper<Peispatient> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("patientcode",patientcodes);
        PageParam<Peispatient> printNoticePageParam = peispatientMapper.selectPage(page, queryWrapper);
        List<Peispatient> records = printNoticePageParam.getRecords();
        return records;
    }
}

