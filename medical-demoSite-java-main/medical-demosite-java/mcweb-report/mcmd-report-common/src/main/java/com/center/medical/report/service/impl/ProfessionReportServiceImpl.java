package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.bean.model.Report;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.bean.vo.PeispatientVo;
import com.center.medical.report.dao.ReportPrintingMapper;
import com.center.medical.report.service.ProfessionReportService;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.OtherReportService;
import com.center.medical.service.PeisStateService;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:52
 */
@Slf4j
@Service("professionReportService")
@RequiredArgsConstructor
public class ProfessionReportServiceImpl extends ServiceImpl<PeispatientMapper, Peispatient> implements ProfessionReportService {

    private final ReportPrintingMapper reportPrintingMapper;
    private final PeisStateService peisStateService;
    private final ReportService reportService;

    private final OtherReportService otherReportService;
    private final ISysBranchService iSysBranchService;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[QT体检者表]列表的职业报告数据
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
            List<PeispatientVo> newList = reportPrintingMapper.getDiseaseReportDataNew(param);
            List<PeispatientVo> oldList = reportPrintingMapper.getDiseaseReportDataOld(param);
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
            iPage = reportPrintingMapper.getDiseaseReportData(page, param);
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
        for (String code : patientcodes){
            Peispatient patient = reportPrintingMapper.getBypatientCode(code);
            if (patient == null) {
                throw new ServiceException("体检者信息不存在:" + code);
            }
            if (dh.intValue() == 1) {
                if (patient.getZytjzt() != null && patient.getZytjzt().intValue() == 1) {
                    patient.setZytjzt(2);
                    peisStateService.setScbs(patient.getPatientcode(),0);
                }
            } else {
                if (patient.getJktjzt() != null && patient.getJktjzt().intValue() == 1) {
                    patient.setJktjzt(2);
                    peisStateService.setScbs(patient.getPatientcode(),0);
                }
            }
            Report report = reportService.getInfoByPatientcode(patientcode,dh);
            if (report == null) {
                throw new ServiceException("报告不存在:" + code);
            }
            report.setIsTotal(1);
            report.setPrintNum(report.getPrintNum() == null ? 1 : (report.getPrintNum() + 1));
            if (report.getStatus() != null && report.getStatus().intValue() == 1) {
                report.setStatus(2);
            }
            reportService.updateById(report);

        }
        return Boolean.TRUE;
    }

}

