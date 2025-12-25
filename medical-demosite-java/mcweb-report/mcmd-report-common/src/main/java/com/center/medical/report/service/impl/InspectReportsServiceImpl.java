package com.center.medical.report.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.InspectReport;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.InspectReportMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.bean.dto.InDataDto;
import com.center.medical.report.bean.dto.InfoSqlDto;
import com.center.medical.report.bean.dto.InspectionReportConfig;
import com.center.medical.report.bean.param.IRParam;
import com.center.medical.report.bean.param.inspectReportsParam;
import com.center.medical.report.bean.vo.IRPageVo;
import com.center.medical.report.bean.vo.InfoListDataVo;
import com.center.medical.report.dao.InspectReportsMapper;
import com.center.medical.report.service.InspectReportsService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 检验报告生成记录(InspectReport)表服务实现类
 *
 * @author ay
 * @since 2023-07-11 09:27:32
 */
@Slf4j
@Service("inspectReportsService")
@RequiredArgsConstructor
public class InspectReportsServiceImpl extends ServiceImpl<InspectReportsMapper, InspectReport> implements InspectReportsService {

    private final InspectReportsMapper inspectReportsMapper;
    private final ISysConfigService sysConfigService;
    private final PeispatientMapper peispatientMapper;
    private final InspectReportMapper inspectReportMapper;
    private final ReportContentService reportContentService;

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param InspectReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<IRPageVo> getList(PageParam<IRPageVo> page, IRParam param) {
        return inspectReportsMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public InspectReport getInfoById(String id) {
        return inspectReportsMapper.getInfoById(id);
    }

    /**
     * 获取右侧详情
     *
     * @param page
     * @param patientcode
     * @return
     */
    @Override
    public IPage<InfoListDataVo> getInfoListData(PageParam<InfoListDataVo> page, String patientcode) {
        return inspectReportsMapper.getInfoListData(page, patientcode);
    }


    /**
     * 生成检验报告
     *
     * @param param
     * @return
     */
    @Override
    public Boolean create(inspectReportsParam param) {
        List<String> patientCodes = param.getPatientCodes();
        InspectionReportConfig title = sysConfigService.getSysConfigObject(Constants.INSPECTION_REPORT_CONFIG, InspectionReportConfig.class);

        for (int i = 0; i < patientCodes.size(); i++) {
            String patientcode = patientCodes.get(i);
            Map<String, Object> data = new HashMap<String, Object>();
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            data.put("patientname", patient.getPatientname());
            data.put("age", patient.getAge() == null ? "" : patient.getAge().intValue());
            data.put("sex", patient.getIdSex() == null ? "" : Render.getSex(patient.getIdSex()));
            data.put("patientcode", patientcode);
            data.put("title", title);//标题可以换行，但占用控件，半页A4纸要显示25条记录，建议一行
            String sign = title.getCenterSign();
            //获取数据
            List<InfoSqlDto> list = inspectReportsMapper.findInfoSql(patientcode);
            if (list.size() > 0) {
                InfoSqlDto os = list.get(0);
                //LIS样本编号
                data.put("lisybbh", os.getLisybbh() == null ? "" : os.getLisybbh().replaceAll("\\.0+$", ""));
                //如果不是本中心的，不能显示本中心医生的签名
                if ("1".equals(sign)) {
                    data.put("rummager", os.getInspectName() == null ? "" : os.getInspectName());
                    data.put("auditer", os.getAuditName() == null ? "" : os.getAuditName());
                }
                data.put("rummagerTime", os.getReceiveDate() == null ? "" : os.getReceiveDate());
                data.put("auditTime", os.getAuditDate() == null ? "" : os.getAuditDate());
            }
            List<InDataDto> exams = new ArrayList<InDataDto>();
            for (InfoSqlDto os : list) {
                String examName = os.getExamitemNameprn();
                String examEnName = os.getExamitemNameeng();
                String examTotalName = StringUtils.isEmpty(examEnName) ?
                        examName :
                        (examName + " " + examEnName);
                String status = Render.getString(os.getStatus());
                exams.add(new InDataDto(
                        examTotalName
                        , os.getExamitemvaluesreport()
                        , (("↑".equals(status) || "↓".equals(status)) ? status : "")
                        , os.getRefrange()
                        , os.getUnits()
                ));
            }
            data.put("exams", exams);

            //更新报告内容
            //生成的报告,插入到报告内容表中
            String jsonString = JSON.toJSONString(data);
            reportContentService.createReportContent(jsonString, 9, patientcode, String.valueOf(patient.getIdExamtype()), null, null, null, null);


            //添加生成记录
            InspectReport ir = inspectReportMapper.selectOne(new LambdaQueryWrapper<InspectReport>()
                    .eq(InspectReport::getPatientcode, patientcode));
            if (ir == null) {
                ir = new InspectReport();
                ir.setPatientcode(patientcode);
                ir.setPrintCount(1);
                ir.setPrintTime(new Date());
                inspectReportMapper.insert(ir);
            } else {
                ir.setPrintCount(ir.getPrintCount().intValue() + 1);
                ir.setPrintTime(new Date());
                inspectReportMapper.updateById(ir);
            }

        }
        return Boolean.TRUE;
    }
}
