package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.OtherReport;
import com.center.medical.bean.model.PeisState;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.report.bean.vo.PeispatientVo;
import com.center.medical.report.dao.ReportPrintingMapper;
import com.center.medical.report.service.HealthReportService;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.OtherReportService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("healthReportService")
@RequiredArgsConstructor
public class HealthReportServiceImpl extends ServiceImpl<PeispatientMapper, Peispatient> implements HealthReportService {

    private final ReportPrintingMapper reportPrintingMapper;
    private final OtherReportService otherReportService;
    private final ReportService reportService;
    private final PeisStateService peisStateService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final SysUserMapper sysUserMapper;
    /**
     * 分页查询[QT体检者表]列表的健康报告数据
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientVo> getPage(PageParam<Peispatient> page, PeispatientParam param) {
        //补全体检号
        if (ObjectUtils.isNotEmpty(param.getPatientcode()) && "true".equals(param.getAutoFill())){
            param.setPatientcode(ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null)));
        }
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            //决策管理查所有的数据
        }else if (SecurityUtils.isLeader()){
            //领导查他的下级数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            //查询自己的订单
            param.setXsjlid(SecurityUtils.getUserNo());
            param.setUserName(SecurityUtils.getUsername());
        }

        IPage<PeispatientVo> iPage = new PageParam<>();
        //是否包含老系统
        if (ObjectUtils.isNotEmpty(param.getContainOldSystem()) && 1 == param.getContainOldSystem()){
            List<PeispatientVo> newList = reportPrintingMapper.getHealthReportDataNew(param);
            List<PeispatientVo> oldList = reportPrintingMapper.getHealthReportDataOld(param);
            newList.addAll(oldList);
            //按照生成时间排序
            Comparator<PeispatientVo> comparator = new Comparator<PeispatientVo>() {
                @Override
                public int compare(PeispatientVo obj1, PeispatientVo obj2) {
                    // 处理空值情况
                    if (obj1.getGenerateDate() == null && obj2.getGenerateDate() == null) {
                        return 0;
                    } else if (obj1.getGenerateDate() == null) {
                        return 1;
                    } else if (obj2.getGenerateDate() == null) {
                        return -1;
                    }
                    // 默认比较规则
                    return obj2.getGenerateDate().compareTo(obj1.getGenerateDate());
                }
            };
            Collections.sort(newList, comparator);
            //设置分页返回数据
            iPage = ToolUtil.getPages((int) page.getCurrent(), (int) page.getSize(), newList);
        }else {
            iPage = reportPrintingMapper.getHealthReportData(page, param);
        }


        //设置序号
        int current = page.getCurrent()==0? 0 : Math.toIntExact(page.getSize() * (page.getCurrent()-1));
        List<PeispatientVo> list = iPage.getRecords();
        int i = 1;
        for (PeispatientVo vo : list) {
            vo.setRownum(current+i);
            i++;
        }
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return reportPrintingMapper.getInfoById(id);
    }

    /**
     * 增加打印次数
     *
     * @param patientcode,diseaseHealth 体检号，类型
     * @return 详情信息
     */
    @Override
    public Boolean append(String patientcode, Integer dh) {
        String[] patientcodes = patientcode.split(",");
        for (String code : patientcodes) {
            Peispatient patient = reportPrintingMapper.getBypatientCode(code);
            if (patient == null) {
                throw new ServiceException("体检者信息不存在:" + code);
            }
            if (dh.intValue() == 1) {
                if (patient.getZytjzt() != null && patient.getZytjzt().intValue() == 1) {
                    patient.setZytjzt(2);
                }
            } else {
                if (patient.getJktjzt() != null && patient.getJktjzt().intValue() == 1) {
                    patient.setJktjzt(2);
                }
            }
            Report report = reportService.getInfoByPatientcode(code, dh);
            if (report == null) {
                throw new ServiceException("报告不存在:" + code);
            }
            report.setIsTotal(1);
            report.setPrintNum(report.getPrintNum() == null ? 1 : (report.getPrintNum() + 1));
            if (report.getStatus() != null && report.getStatus().intValue() == 1) {
                report.setStatus(2);
            }
            //保存打印时间和打印人，只保存第一次的
            if (StringUtils.isEmpty(report.getPrintMan())){
                report.setPrintTime(new Date());
                report.setPrintId(SecurityUtils.getUserNo());
                report.setPrintMan(SecurityUtils.getUsername());
            }
            reportService.updateById(report);
        }
        return Boolean.TRUE;
    }

    /**
     * 健康报告，未交接的，报告发放方式为电子版报告，点击电子报告处理，直接已交接。
     *
     * @param reportIds
     * @return 详情信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void electronicReportHandover(String reportIds) {
        if (StringUtils.isEmpty(reportIds)) {
            throw new ServiceException("请选择要一条或多条报告！");
        }
        String[] rIds = reportIds.split(",");
        Date now = new Date();
        String userNo = SecurityUtils.getUserNo();
        String userName = SecurityUtils.getUsername();
        for (String id : rIds) {
            Report report = reportService.getInfoById(id);
            if (report.getStatus() != null && report.getStatus() >= 9) {
                throw new ServiceException("请选择未交接的报告！");
            }
            if (!"4".equals(report.getGrantId())) {
                throw new ServiceException("请选择报告发放方式为【电子版报告】的报告！");
            }
            Peispatient patient = reportPrintingMapper.getBypatientCode(report.getPatientcode());
            if (patient.getJktjzt() == null || patient.getJktjzt() < 1) {
                throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,处理失败!");
            }
            patient.setIdInformway(report.getGrantId());
            patient.setJktjzt(9);
            peispatientService.updateById(patient);
            peisStateService.setScbs(patient.getPatientcode(), 0);

            report.setStatus(9);
            report.setTbbz(0);
            report.setRevTime(now);
            report.setJoinPersonMan(userName);
            report.setJoinPersonId(userNo);
            report.setRevPersonMan(userName);
            report.setRevPersonId(userNo);
            report.setIsTotal(1);//已打印，不设置不能反交接
            reportService.updateById(report);
        }
    }

    /**
     * 终审交接
     *
     * @param patientcodes 体检号
     * @return 所有数据
     */
    @Override
    public List<Peispatient> searchZsjjCode(String patientcodes) {
        List<Peispatient> data = new ArrayList<Peispatient>();
        for (String patientcode : patientcodes.split(",")) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
            Peispatient patient = reportPrintingMapper.getBypatientCode(patientcode);
            //体检者表
            if (ObjectUtils.isEmpty(patient)) {
                throw new ServiceException("体检号为" + patientcode + "的体检者不存在！");
            }
            //查询有无隐私项目
            Integer integer = reportPrintingMapper.containsPrivate(patientcode);
            if (integer>0){
                //有隐私项目判断报告是否生成
                OtherReport or = otherReportService.getInfoByCode(patient.getPatientcode(), "2");
                if (or == null || or.getPdfUrl() == null) {
                    throw new ServiceException("体检号" + patient.getPatientcode()
                            + "的隐私报告未生成！");
                }
            }
            if (!"0".equals(patient.getIdExamtype()) && !"2".equals(patient.getIdExamtype())) {
                throw new ServiceException("体检号" + patientcode + "的体检类型不能终审交接！");
            }
            if (patient.getJktjzt() != null && (patient.getJktjzt() == 7 || patient.getJktjzt() > 8)) {
                throw new ServiceException("体检号" + patientcode + "已终审，无需终审交接！");
            }
            if (patient.getFReadytofinal() == null || patient.getFReadytofinal() != 1) {
                throw new ServiceException("体检号" + patientcode + "分检未完成，不能终审交接！");
            }
            data.add(patient);
        }
        return data;
    }

    /**
     * 终审交接保存
     *
     * @param ids id,逗号隔开
     * @return 所有数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveZsjj(String ids) {
        String userNo = SecurityUtils.getUserNo();
        String userName = SecurityUtils.getUsername();
        Date now = new Date();
        for (String id : ids.split(",")) {
            Peispatient patient = reportPrintingMapper.getInfoById(id);
            if (patient == null) {
                throw new ServiceException("体检者不存在！");
            }
            //查询有无隐私项目
            Integer integer = reportPrintingMapper.containsPrivate(id);
            if (integer>0) {
                OtherReport or = otherReportService.getInfoByCode(patient.getPatientcode(), "2");
                if (or == null || or.getPdfUrl() == null) {
                    throw new ServiceException("体检号" + patient.getPatientcode()
                            + "的隐私报告未生成！");
                }
            }
            String patientcode = patient.getPatientcode();
            if (!"0".equals(patient.getIdExamtype()) && !"2".equals(patient.getIdExamtype())) {
                throw new ServiceException("体检号" + patientcode + "的体检类型不能终审交接！");
            }
            if (patient.getJktjzt() != null && (patient.getJktjzt() == 7 || patient.getJktjzt() > 8)) {
                throw new ServiceException("体检号" + patientcode + "已终审，无需终审交接！");
            }
            if (patient.getFReadytofinal() == null || patient.getFReadytofinal() != 1) {
                throw new ServiceException("体检号" + patientcode + "分检未完成，不能终审交接！");
            }
            patient.setJktjzt(7);
            peispatientService.updateById(patient);

            PeisState ps = peisStateService.setScbs(patient.getPatientcode(), 0);
            ps.setIdGuidancereturnedby(0);//收费项目上传标识

            Report report = reportService.getInfoByPatientcode(patientcode, 0);
            if (report == null) {
                report = new Report();
                report.setDiseaseHealth(0);
                report.setPatientcode(patientcode);
                report.setShortCode(ToolUtil.getShortCodeByLong(patientcode));
                report.setIsPrintMessage(0);
                report.setCreateNum(1);
            }
            report.setStatus(7);//终审通过
            report.setIsTotal(1);
            report.setGrantId(patient.getIdPayway());
            report.setPatientname(patient.getPatientname());
            report.setAge((int) Math.floor(patient.getAge()));
            report.setIdOrg(patient.getIdOrg());
            report.setOrgName(patient.getOrgName());
            report.setDateregister(patient.getDateregister());
            report.setPhone(patient.getPhone());
            report.setIdOpendoctor(patient.getIdOpendoctor());
            report.setDoctorapply(patient.getDoctorapply());
            if (StringUtils.isNotBlank(patient.getNumorgresv()) && !"-1".equals(patient.getNumorgresv())) {
                report.setNumorgresv(patient.getNumorgresv());
            } else {
                report.setNumorgresv(null);
            }
            report.setSex(patient.getIdSex());
            report.setLastId(userNo);
            report.setLastMan(userName);
            report.setLastTime(now);
            report.setGenerateDate(null);
            report.setGenerateHint(null);

//            OtherReport or = otherReportService.getInfoByCode(patient.getPatientcode(), "2");
//            if (ObjectUtils.isNotEmpty(or)){
//                report.setConfigId(or.getConfigId());
//                report.setUrlPdf(or.getPdfUrl());
//            }

            if (report.getId() == null) {
                reportService.save(report);
            } else {
                reportService.updateById(report);
            }
        }
        return Boolean.TRUE;
    }


}

