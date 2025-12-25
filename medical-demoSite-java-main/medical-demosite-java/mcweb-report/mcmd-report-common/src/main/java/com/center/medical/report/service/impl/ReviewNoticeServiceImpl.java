package com.center.medical.report.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.BaseWorktype;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.service.BaseWorktypeService;
import com.center.medical.data.service.HarmService;
import com.center.medical.report.bean.dto.AllContraindicatedDto;
import com.center.medical.report.bean.dto.AllDiseaseDataDto;
import com.center.medical.report.bean.dto.AllReviewDataDto;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.bean.vo.createReviewVo;
import com.center.medical.report.dao.CommentsProgessionalMapper;
import com.center.medical.report.dao.ReviewNoticeMapper;
import com.center.medical.report.service.ReportConfigService;
import com.center.medical.report.service.ReviewNoticeService;
import com.center.medical.report.service.SectionTotalService;
import com.center.medical.report.service.TotalHealthInspectionService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BG报告主表(Report)表服务实现类
 *
 * @author ay
 * @since 2023-04-26 17:43:49
 */
@Slf4j
@Service("reviewNoticeService")
@RequiredArgsConstructor
public class ReviewNoticeServiceImpl extends ServiceImpl<ReviewNoticeMapper, Report> implements ReviewNoticeService {

    private final ReviewNoticeMapper reviewNoticeMapper;
    private final PeispatientMapper peispatientMapper;
    private final CommentsProgessionalMapper commentsProgessionalMapper;
    private final ReportContentService reportContentService;
    private final BaseWorktypeService baseWorktypeService;
    private final HarmService harmService;
    private final ReportConfigService reportConfigService;
    private final ISysBranchService sysBranchService;
    private final TotalHealthInspectionService totalHealthInspectionService;
    private final SectionTotalService sectionTotalService;


    private SimpleDateFormat taday = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");


    /**
     * 查询复查通知单
     *
     * @param patientno
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public createReviewVo createReview(String patientno, String idPatientclass) {
        createReviewVo vo = new createReviewVo();
        if (!StringUtils.isEmpty(patientno)) {
            //体检者数据
            Peispatient pei = peispatientMapper.getByPatientCode(patientno);
            if (pei != null) {
//                //获取所有职业处理意见分类号码
//                List<String> serialNo = commentsProgessionalMapper.findAllSerialNo(patientno);
                if (ObjectUtils.isNotEmpty(idPatientclass)) {
                    // 可疑职业病数据
                    if ("1".equals(idPatientclass)) {
                        Map<String, Object> zyb = expDisease(patientno);
                        vo.setZyb(zyb);
                    } else if ("2".equals(idPatientclass)) {
                        // 职业禁忌证数据
                        Map<String, Object> contraindicated = expContraindicated(patientno);
                        vo.setJjz(contraindicated);
                    } else if ("3".equals(idPatientclass)) {
                        // 复查通知单数据
                        Map<String, Object> result = expReviewIreportTl(patientno);
                        vo.setFc(result);
                    }
                }
                //复查通知单生成状态 0未生成 1生成中 2已生成
                pei.setCountreportoccupationpdf(2);
                peispatientMapper.updateById(pei);

                //保存到报告生成内容中
                String jsonString = JSON.toJSONString(vo);
                reportContentService.createReportContent(jsonString, 8, patientno, idPatientclass, null, null, null, null);
            }
        }
        return vo;
    }

    /**
     * 复查通知单数据
     *
     * @param patientno
     * @return
     */
    private Map<String, Object> expReviewIreportTl(String patientno) {
        //获取所有复查模板上所需要的数据
        Map<String, Object> data = findAllReviewData(patientno);// 参数
        return data;

    }

    /**
     * 获取所有复查模板上所需要的数据
     *
     * @param patientno
     * @return
     */
    private Map<String, Object> findAllReviewData(String patientno) {
        List<AllReviewDataDto> list = reviewNoticeMapper.findAllReviewData(patientno);

        Map<String, Object> params = new HashMap<String, Object>();
        if (list != null && list.size() > 0) {
            AllReviewDataDto obj = list.get(0);
            params.put("name", obj.getPatientname() != null ? String.valueOf(obj.getPatientname()) : "");
            String com = obj.getOrgName() != null ? String.valueOf(obj.getOrgName()) : "";
            String orgName = obj.getOo() != null || obj.getOo().toString().length() == 0
                    ? String.valueOf(obj.getOo())
                    : "";
            params.put("company", com.equals(orgName) || orgName.length() == 0 ? com
                    : (com + "（" + orgName + "）"));
            String abnormal = obj.getPosistive() != null ? String.valueOf(obj.getPosistive()) : "";
            if (abnormal.startsWith("\n")) {
                abnormal = abnormal.substring(1);
            }
            abnormal = abnormal.replaceAll("\t", "");
            abnormal = abnormal.replaceAll("\n", "");
            params.put("abnormal", abnormal);
            params.put("reviewDate", obj.getDateTo() != null ? obj.getDateTo() : "");
            params.put("items", obj.getItemsName() != null ? String.valueOf(obj.getItemsName()) : "");
            String notice = obj.getNoticeOfProceedingText() != null ? String.valueOf(obj.getNoticeOfProceedingText()) : "";
            notice = notice.replaceAll("\n", "");
            params.put("notice", notice);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
            params.put("nowdate", simpleDateFormat.format(new Date()));

            String dateStart = obj.getStartDate() != null ? sdf.format(obj.getStartDate()) : "";
            params.put("dateStart", dateStart);
            String dateEnd = "";
            try {
                if (StringUtils.isNotEmpty(dateStart)) {

                    Calendar c = Calendar.getInstance();
                    c.setTime(sdf.parse(dateStart));
                    c.add(Calendar.MONTH, 1);
                    dateEnd = sdf.format(c.getTime());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("dateEnd", dateEnd);
            params.put("totalDate", ObjectUtils.isNotEmpty(obj.getTotalDate()) ? sdf.format(obj.getTotalDate()) : "年  月  日");
            String compony = Constants.REVIEWCOMPONY;
            params.put("hospital", StringUtils.isEmpty(compony)
                    ? "青岛沃德国际医疗健康产业股份有限公司综合门诊部" : compony);


            Peispatient peispatient = peispatientMapper.getByPatientCode(patientno);
            params.put("patientcode", peispatient.getPatientcode());
            params.put("idcardno", peispatient.getIdcardno());
            BaseWorktype baseWorktype = baseWorktypeService.getInfoById(peispatient.getWorktypeId());
            params.put("trades", ObjectUtils.isNotEmpty(baseWorktype) ? baseWorktype.getTypeName() : null);
            params.put("jhgln", peispatient.getJhgl() / 12);
            params.put("jhgly", peispatient.getJhgl() % 12);
            List<Harm> harmList = harmService.list(new QueryWrapper<Harm>().in("id", peispatient.getJhys().split(",")));
            String harmName = harmList.stream().map(Harm::getHarmName).collect(Collectors.joining(","));
            params.put("harmName", harmName);
            String tjlxName = Render.getMedicalType(peispatient.getMedicaltype());
            params.put("medicaltype", tjlxName);

        }
        return params;
    }

    /**
     * 职业禁忌证数据
     *
     * @param patientno
     * @return
     */
    private Map<String, Object> expContraindicated(String patientno) {
        //获取所有职业禁忌证模板上所需要的数据
        Map<String, Object> params = findAllContraindicatedData(patientno);
        return params;
    }

    /**
     * 获取所有职业禁忌证模板上所需要的数据
     *
     * @param patientno
     * @return
     */
    private Map<String, Object> findAllContraindicatedData(String patientno) {
        List<AllContraindicatedDto> list = reviewNoticeMapper.findAllContraindicatedData(patientno);
        Map<String, Object> params = new HashMap<String, Object>();
        if (list != null && list.size() > 0) {
            AllContraindicatedDto obj = list.get(0);
            params.put("orgname", obj.getOrgName() != null ? String.valueOf(obj.getOrgName()) : "");
            params.put("name", obj.getPatientname() != null ? String.valueOf(obj.getPatientname()) : "");
            String date = obj.getDateregister() != null ? obj.getDateregister() : "";
            params.put("date", date);
            if (!"".equals(date)) {
                String[] arr = date.split("-");
                params.put("year", arr[0]);
                params.put("month", arr[1]);
                params.put("day", arr[2]);
            } else {
                params.put("year", "");
                params.put("month", "");
                params.put("day", "");
            }
            params.put("result", obj.getDiagnosis() != null ? String.valueOf(obj.getDiagnosis()) : "");
            params.put("reason", obj.getHarmName() != null ? String.valueOf(obj.getHarmName()) : "");
            params.put("sysdate", new SimpleDateFormat("yyyy'年'MM'月'dd'日'")
                    .format(new Date()));
            params.put("medicaltype", Render.getMedicalType(obj.getMedicaltype()));


            Peispatient peispatient = peispatientMapper.getByPatientCode(patientno);
            params.put("patientcode", peispatient.getPatientcode());
            params.put("idcardno", peispatient.getIdcardno());
            BaseWorktype baseWorktype = baseWorktypeService.getInfoById(peispatient.getWorktypeId());
            params.put("trades", ObjectUtils.isNotEmpty(baseWorktype) ? baseWorktype.getTypeName() : null);
            params.put("jhgl", peispatient.getJhgl());
            List<Harm> harmList = harmService.list(new QueryWrapper<Harm>().in("id", peispatient.getJhys().split(",")));
            String harmName = harmList.stream().map(Harm::getHarmName).collect(Collectors.joining(","));
            params.put("harmName", harmName);


            String content = reportConfigService.getBranchConfig(sysBranchService.getDefaultBranch().getBranchId());
            if (StringUtils.isNotEmpty(content)) {
                ReportConfigVo configVo = JSONObject.parseObject(content, ReportConfigVo.class);
                params.put("branch",configVo.getProducer());
            }



        }
        return params;
    }

    /**
     * 可疑职业病数据
     *
     * @param patientno
     * @return
     */
    private Map<String, Object> expDisease(String patientno) {
        //获取所有可疑职业病模板上所需要的数据
        Map<String, Object> params = findAllDiseaseData(patientno);
        return params;
    }

    /**
     * 获取所有可疑职业病模板上所需要的数据
     *
     * @param patientno
     * @return
     */
    private Map<String, Object> findAllDiseaseData(String patientno) {
        List<AllDiseaseDataDto> list = reviewNoticeMapper.findAllDiseaseData(patientno);
        Map<String, Object> params = new HashMap<String, Object>();
        if (list != null && list.size() > 0) {
            AllDiseaseDataDto obj = list.get(0);
            params.put("orgname", obj.getOrgName() != null ? String.valueOf(obj.getOrgName()) : "");
            params.put("name", obj.getPatientname() != null ? String.valueOf(obj.getPatientname()) : "");
            String date = obj.getDateregister() != null ? obj.getDateregister() : "";
            params.put("date", date);
            if (!"".equals(date)) {
                String[] arr = date.split("-");
                params.put("year", arr[0]);
                params.put("month", arr[1]);
                params.put("day", arr[2]);
            } else {
                params.put("year", "");
                params.put("month", "");
                params.put("day", "");
            }
            params.put("result", obj.getAbnormal() != null ? "疑似"+ obj.getAbnormal() : "");
            params.put("enddate", obj.getDateTo() != null ? String.valueOf(obj.getDateTo()) : "");
            params.put("sysdate", new SimpleDateFormat("yyyy'年'MM'月'dd'日'")
                    .format(new Date()));
            params.put("medicaltype", Render.getMedicalType(obj.getMedicaltype()));

            Peispatient peispatient = peispatientMapper.getByPatientCode(patientno);
            params.put("patientcode", peispatient.getPatientcode());
            params.put("idcardno", peispatient.getIdcardno());

            String content = reportConfigService.getBranchConfig(sysBranchService.getDefaultBranch().getBranchId());
            if (StringUtils.isNotEmpty(content)) {
                ReportConfigVo configVo = JSONObject.parseObject(content, ReportConfigVo.class);
                params.put("branch",configVo.getProducer());
            }
            // 总检主表
            SectionTotal sectionTotal = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", peispatient.getPatientcode()).eq("disease_health", 1));
            if (ObjectUtils.isNotEmpty(sectionTotal)){
                Map<String, String> map = totalHealthInspectionService.generateInfo(sectionTotal, 1);
                params.put("posistiveDepts", map.get("posistiveDepts") + "异常");
            }

        }
        return params;
    }
}

