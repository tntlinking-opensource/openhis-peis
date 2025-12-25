package com.center.medical.report.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.bean.param.HealthAuditParam;
import com.center.medical.report.dao.HealthAuditMapper;
import com.center.medical.report.dao.ReportPrintingMapper;
import com.center.medical.report.service.HealthReportService;
import com.center.medical.report.service.ProfessionLastAuditService;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeisStateService;
import com.center.medical.statistics.service.ProfessionUploadResultService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 职业终审服务实现类
 *
 * @author 浮俊杰
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("professionLastAuditService")
@RequiredArgsConstructor
public class ProfessionLastAuditServiceImpl extends ServiceImpl<HealthAuditMapper, Report> implements ProfessionLastAuditService {

    private final HealthAuditMapper healthAuditMapper;
    private final ReportService reportService;
    private final ReportPrintingMapper reportPrintingMapper;
    private final HealthReportService healthReportService;
    private final PeisStateService peisStateService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final ProfessionUploadResultService professionUploadResultService;
    private final ISysConfigService iSysConfigService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    /**
     * 分页查询职业终审页面数据
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getListData(PageParam<Peispatient> page, HealthAuditParam param) {
        //体检号去空格
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim());
        }
        //补全体检号
        if (param.getAutoFill() != null && param.getAutoFill() != "") {
            param.setPatientcode(ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null)));
        }
        return healthAuditMapper.getProfessionLastListData(page, param);
    }

    /**
     * 职业终审页面数据通过
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @Override
    public Boolean pass(String ids) {
        List<String> patientCodes = new ArrayList<>();
        for (String id : ids.split(",")) {
            Report report = reportService.getInfoById(id);
            if (report != null) {
                //更新近期体检者表报告状态
                Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
                if (patient != null) {
                    if (patient.getZytjzt() == null || patient.getZytjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,审核失败!");
                    }
                    patient.setZytjzt(7);
                    peispatientMapper.updateById(patient);
                    peisStateService.setScbs(patient.getPatientcode(), 0);

                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,审核失败!");
                    }
                    /** 终审人ID */
                    report.setLastId(SecurityUtils.getUserNo());
                    /** 终审人名称 */
                    report.setLastMan(SecurityUtils.getUsername());
                    /** 终审时间 */
                    report.setLastTime(new Date());
                    /** 修改时间 */
                    report.setModifydate(new Date());
                    /** 0:总检开始、1:总检完成、2:报告已打印、
                     * 3:报告一审通过、4:报告一审不通过、
                     * 5:二审通过、6:二审不通过、
                     * 7:终审通过、8:终审不通过、
                     * 9:报告已交接、10:报告已通知、11:报告已领取 */
                    report.setStatus(7);
                    this.updateById(report);
                    patientCodes.add(report.getPatientcode());
                } else {
                    throw new ServiceException("体检表中未找到体检号为" + report.getPatientcode() + ",名称为" + report.getPatientname() + "的体检者");
                }
            } else {
                throw new ServiceException("编号:【" + id + "】的报告在数据库中未检索到,反审失败!");
            }
        }
        if (CollectionUtils.isNotEmpty(patientCodes)){
            //立即上传疾控
            updateStatus(patientCodes);
        }
        return Boolean.TRUE;
    }

    private void updateStatus(List<String> patientcodes) {
        //修改状态
        professionUploadResultService.updateStatus(patientcodes);

        //调用接口，立刻重传
        if(patientcodes!=null&&patientcodes.size()>0){
            log.info("重新上传开始发送请求！");
            log.info("体检号:{}",patientcodes);
            String professionDomain=iSysConfigService.getDomain().getProfessionDomain();
            if(StrUtil.isNotEmpty(professionDomain)){
                executorService.submit(() -> {
                    try {
                        HttpRequest.post(professionDomain+"/send/multi")
                                .timeout(10*1000)
                                .contentType(ContentType.JSON.getValue())
                                .body(JSONUtil.toJsonStr(patientcodes))
                                .execute();
                    } catch (Exception e) {
                        log.error("重新上传请求失败:{}", JSONUtil.toJsonStr(e));
                    }

                });
            }
        }
    }

    /**
     * 职业终审页面数据反审
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @Override
    public Boolean unaudit(String ids) {
        for (String id : ids.split(",")) {
            Report report = reportService.getInfoById(id);
            if (report != null) {
                if (report.getStatus() != 7) {
                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,反审失败!");
                    }
                } else {
                    //更新近期体检者表报告状态
                    Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
                    if (patient != null) {
                        if (patient.getZytjzt() == null || patient.getZytjzt() < 1) {
                            throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,反审失败!");
                        }
                        patient.setZytjzt(5);
                        peispatientMapper.updateById(patient);
                        peisStateService.setScbs(patient.getPatientcode(), 0);

                        /** 终审人ID */
                        report.setLastId(SecurityUtils.getUserNo());
                        /** 终审人名称 */
                        report.setLastMan(SecurityUtils.getUsername());
                        /** 终审时间 */
                        report.setLastTime(new Date());
                        /** 修改时间 */
                        report.setModifydate(new Date());
                        report.setStatus(5);
                        this.updateById(report);
                    } else {
                        throw new ServiceException("体检表中未找到体检号为" + report.getPatientcode() + ",名称为" + report.getPatientname() + "的体检者");
                    }
                }
            } else {
                throw new ServiceException("编号:【" + id + "】的报告在数据库中未检索到,反审失败!");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 职业终审页面数据不通过
     *
     * @param id, firstReason    查询条件
     * @return 所有数据
     */
    @Override
    public String uncheck(String id, String lastReason) {
        if (id != null) {
            Report report = reportService.getInfoById(id);
            if (report != null) {
                //更新近期体检者表报告状态
                Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
                if (patient != null) {
                    if (patient.getZytjzt() == null || patient.getZytjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,审核失败!");
                    }

                    patient.setZytjzt(8);
                    peispatientMapper.updateById(patient);
                    peisStateService.setScbs(patient.getPatientcode(), 0);
                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,审核失败!");
                    }
                    report.setStatus(8);
                    report.setSecondReason(lastReason);
                    /** 终审人ID */
                    report.setLastId(SecurityUtils.getUserNo());
                    /** 终审人名称 */
                    report.setLastMan(SecurityUtils.getUsername());
                    /** 终审时间 */
                    report.setLastTime(new Date());
                    /** 修改时间 */
                    report.setModifydate(new Date());
                    this.updateById(report);
                } else {
                    throw new ServiceException("体检表中未找到体检号为" + report.getPatientcode() + ",名称为" + report.getPatientname() + "的体检者");
                }
            } else {
                return "保存失败:未找到相应体检者报告!";
            }
        } else {
            return "保存失败:页面提交的体检者报告数据异常!";
        }
        return "SUCCESS";
    }

    /**
     * 职业终审获取体检者数据
     *
     * @param patientcode 查询条件
     * @return 所有数据
     */
    @Override
    public Peispatient getPatientData(String patientcode) {
        return reportPrintingMapper.getBypatientCode(patientcode);
    }

    /**
     * 批量通过职业终审
     *
     * @param peispatients 实体对象
     * @return 修改结果
     */
    @Override
    public String batchPass(List<Peispatient> peispatients) {
        for (Peispatient p : peispatients) {
            Peispatient patient = reportPrintingMapper.getBypatientCode(p.getPatientcode());
            if (patient != null) {
                if (patient.getZytjzt() == null || patient.getZytjzt() < 1) {
                    throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,审核失败!");
                }
                //更新近期体检者表报告状态
                patient.setZytjzt(7);
                peispatientMapper.updateById(patient);
                peisStateService.setScbs(patient.getPatientcode(), 0);

                Report report = this.reportService.getInfoByPatientcode(p.getPatientcode(), 1);
                if (report != null) {
                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,审核失败!");
                    }
                    /** 终审人ID */
                    report.setLastId(SecurityUtils.getUserNo());
                    /** 终审人名称 */
                    report.setLastMan(SecurityUtils.getUsername());
                    /** 终审时间 */
                    report.setLastTime(new Date());
                    /** 修改时间 */
                    report.setModifydate(new Date());
                    /** 0:总检开始、1:总检完成、2:报告已打印、
                     * 3:报告一审通过、4:报告一审不通过、
                     * 5:二审通过、6:二审不通过、
                     * 7:终审通过、8:终审不通过、
                     * 9:报告已交接、10:报告已通知、11:报告已领取 */
                    report.setStatus(7);
                    this.updateById(report);
                } else {
                    throw new ServiceException("报告中未找到体检号为" + p.getPatientcode() + ",名称为" + p.getPatientname() + "的体检者");
                }
            } else {
                throw new ServiceException("体检表中未找到体检号为" + p.getPatientcode() + ",名称为" + p.getPatientname() + "的体检者");
            }
        }
        return "SUCCESS";
    }
}

