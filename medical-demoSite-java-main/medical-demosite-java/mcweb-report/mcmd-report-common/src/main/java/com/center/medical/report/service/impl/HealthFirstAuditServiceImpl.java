package com.center.medical.report.service.impl;

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
import com.center.medical.report.service.HealthFirstAuditService;
import com.center.medical.report.service.HealthReportService;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeisStateService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 健康一审服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("healthFirstAuditService")
@RequiredArgsConstructor
public class HealthFirstAuditServiceImpl extends ServiceImpl<HealthAuditMapper, Report> implements HealthFirstAuditService {

    private final HealthAuditMapper healthAuditMapper;
    private final ReportService reportService;
    private final ReportPrintingMapper reportPrintingMapper;
    private final HealthReportService healthReportService;
    private final PeisStateService peisStateService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询健康一审页面数据
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getListData(PageParam<Peispatient> page, HealthAuditParam param) {
        //补全体检号
        if (param.getAutoFill() != null && param.getPatientcode() != null) {
            param.setPatientcode(ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null)));
        }
        //如果没传参数
        if (ObjectUtils.isEmpty(param)) {
            param.setFlag("1");
        }
        return healthAuditMapper.getFirstListData(page, param);
    }

    /**
     * 健康一审页面数据通过
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @Override
    public Boolean pass(String ids) {
        for (String id : ids.split(",")) {
            Report report = reportService.getInfoById(id);
            if (report != null) {
                if (report.getStatus() >= 9) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,审核失败!");
                } else if (report.getStatus() >= 7) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进终审阶段,审核失败!");
                } else if (report.getStatus() >= 5) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进二审阶段,审核失败!");
                } else if (report.getStatus() < 2) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告尚未打印,审核失败!");
                }
                //更新近期体检者表报告状态
                Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
                if (patient != null) {
                    if (patient.getJktjzt() == null || patient.getJktjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,审核失败!");
                    }

                    patient.setJktjzt(3);
                    patient.setModifydate(new Date());
                    healthReportService.updateById(patient);
                } else {
                    throw new ServiceException("体检表中未找到体检号为" + report.getPatientcode() + ",名称为" + report.getPatientname() + "的体检者");
                }

                /** 一审人ID */
                report.setFirstId(SecurityUtils.getUserNo());
                /** 一审人名称 */
                report.setFirstMan(SecurityUtils.getUsername());
                /** 一审时间 */
                report.setFirstTime(new Date());
                /** 修改时间 */
                report.setModifydate(new Date());
                /** 0:总检开始、1:总检完成、2:报告已打印、
                 * 3:报告一审通过、4:报告一审不通过、
                 * 5:二审通过、6:二审不通过、
                 * 7:终审通过、8:终审不通过、
                 * 9:报告已交接、10:报告已通知、11:报告已领取 */
                report.setStatus(3);
                this.updateById(report);

            } else {
                throw new ServiceException("编号:【" + id + "】的报告在数据库中未检索到,反审失败!");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 健康一审页面数据反审
     *
     * @param ids 查询条件
     * @return 所有数据
     */
    @Override
    public Boolean unaudit(String ids) {
        for (String id : ids.split(",")) {
            Report report = reportService.getInfoById(id);
            if (report != null) {
                //更新近期体检者表报告状态
                Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
                if (patient != null) {
                    if (patient.getJktjzt() == null || patient.getJktjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,反审失败!");
                    }

                    patient.setJktjzt(2);
                    peisStateService.setScbs(patient.getPatientcode(), 0);
                    healthReportService.updateById(patient);
                } else {
                    throw new ServiceException("体检表中未找到体检号为" + report.getPatientcode() + ",名称为" + report.getPatientname() + "的体检者");
                }

                if (report.getStatus() != 3) {
                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,反审失败!");
                    } else if (report.getStatus() >= 7) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进终审阶段,反审失败!");
                    } else if (report.getStatus() >= 5) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进二审阶段,反审失败!");
                    } else if (report.getStatus() < 2) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告尚未打印,审核失败!");
                    }
                } else {
                    /** 一审人ID */
                    report.setFirstId(SecurityUtils.getUserNo());
                    /** 一审人名称 */
                    report.setFirstMan(SecurityUtils.getUsername());
                    /** 一审时间 */
                    report.setFirstTime(new Date());
                    /** 修改时间 */
                    report.setModifydate(new Date());
                    report.setStatus(2);
                    this.updateById(report);
                }
            } else {
                throw new ServiceException("编号:【" + id + "】的报告在数据库中未检索到,反审失败!");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 健康一审页面数据不通过
     *
     * @param id, firstReason    查询条件
     * @return 所有数据
     */
    @Override
    public String uncheck(String id, String firstReason) {
        if (id != null) {
            Report report = reportService.getInfoById(id);
            if (report != null) {
                //更新近期体检者表报告状态
                Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
                if (patient != null) {
                    if (patient.getJktjzt() == null || patient.getJktjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,审核失败!");
                    }

                    patient.setJktjzt(4);
                    patient.setModifydate(new Date());
                    healthReportService.updateById(patient);
                } else {
                    throw new ServiceException("体检表中未找到体检号为" + report.getPatientcode() + ",名称为" + report.getPatientname() + "的体检者");
                }

                if (report.getStatus() >= 9) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,审核失败!");
                } else if (report.getStatus() >= 7) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进终审阶段,审核失败!");
                } else if (report.getStatus() >= 5) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进二审阶段,审核失败!");
                } else if (report.getStatus() < 2) {
                    throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告尚未打印,审核失败!");
                }
                report.setStatus(4);
                report.setFirstReason(firstReason);
                /** 一审人ID */
                report.setFirstId(SecurityUtils.getUserNo());
                /** 一审人名称 */
                report.setFirstMan(SecurityUtils.getUsername());
                /** 一审时间 */
                report.setFirstTime(new Date());
                /** 修改时间 */
                report.setModifydate(new Date());
                this.updateById(report);
            } else {
                return "保存失败:未找到相应体检者报告!";
            }
        } else {
            return "保存失败:页面提交的体检者报告数据异常!";
        }
        return "SUCCESS";
    }

    /**
     * 健康一审获取体检者数据
     *
     * @param patientcode 查询条件
     * @return 所有数据
     */
    @Override
    public Peispatient getPatientData(String patientcode) {
        return reportPrintingMapper.getBypatientCode(patientcode);
    }

    /**
     * 批量通过健康一审
     *
     * @param peispatients 实体对象
     * @return 修改结果
     */
    @Override
    public String batchPass(List<Peispatient> peispatients) {
        for (Peispatient p : peispatients) {
            Peispatient patient = reportPrintingMapper.getBypatientCode(p.getPatientcode());
            if (patient != null) {
                if (patient.getJktjzt() == null || patient.getJktjzt() < 1) {
                    throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,审核失败!");
                }
                //更新近期体检者表报告状态
                patient.setJktjzt(3);
                patient.setModifydate(new Date());
                peispatientMapper.updateById(patient);

                Report report = this.reportService.getInfoByPatientcode(p.getPatientcode(), 0);
                if (report != null) {
                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,审核失败!");
                    } else if (report.getStatus() >= 7) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进终审阶段,审核失败!");
                    } else if (report.getStatus() >= 5) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已进二审阶段,审核失败!");
                    } else if (report.getStatus() < 2) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告尚未打印,审核失败!");
                    }
                    /** 一审人ID */
                    report.setFirstId(SecurityUtils.getUserNo());
                    /** 一审人名称 */
                    report.setFirstMan(SecurityUtils.getUsername());
                    /** 一审时间 */
                    report.setFirstTime(new Date());
                    /** 修改时间 */
                    report.setModifydate(new Date());
                    /** 0:总检开始、1:总检完成、2:报告已打印、
                     * 3:报告一审通过、4:报告一审不通过、
                     * 5:二审通过、6:二审不通过、
                     * 7:终审通过、8:终审不通过、
                     * 9:报告已交接、10:报告已通知、11:报告已领取 */
                    report.setStatus(3);
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

