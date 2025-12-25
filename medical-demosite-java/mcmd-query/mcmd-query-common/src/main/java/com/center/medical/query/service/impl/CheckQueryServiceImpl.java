package com.center.medical.query.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.SyncFileCheckDto;
import com.center.medical.bean.model.*;
import com.center.medical.bean.param.ConfirmOrderParam;
import com.center.medical.bean.param.OnlineConfirmParam;
import com.center.medical.common.config.Domain;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.dao.OtherReportMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatienthistoryMapper;
import com.center.medical.query.dao.CheckQueryMapper;
import com.center.medical.query.service.CheckQueryService;
import com.center.medical.report.bean.param.IReportParam;
import com.center.medical.report.bean.vo.CreateReportVo;
import com.center.medical.report.dao.ReportMapper;
import com.center.medical.report.service.IPersonalReportService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 登记信息查询(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("checkQueryService")
@RequiredArgsConstructor
public class CheckQueryServiceImpl extends ServiceImpl<CheckQueryMapper, Peispatient> implements CheckQueryService {

    private final CheckQueryMapper checkQueryMapper;
    private final PeispatientMapper peispatientMapper;
    private final OtherReportMapper otherReportMapper;
    private final PeisStateService peisStateService;
    private final ReportMapper reportMapper;
    private final PeispatienthistoryMapper peispatienthistoryMapper;
    private final PeispatientService peispatientService;
    private final IPersonalReportService iPersonalReportService;
    private final ReportContentService reportContentService;
    private final ISysConfigService iSysConfigService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Peispatient> getList(PageParam<Peispatient> page, Peispatient param) {
        return checkQueryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return checkQueryMapper.getInfoById(id);
    }


    /**
     * 终审交接
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean zsjj(List<String> ids) {
        String userName = SecurityUtils.getUsername();
        Date now = new Date();
        String userId = SecurityUtils.getUserNo();
        //循环
        for (String id : ids) {
            //体检者表
            Peispatient patient = peispatientMapper.getInfoById(id);
            if (patient == null) {
                throw new ServiceException("体检者不存在！");
            }

            //其他报告
            OtherReport or = otherReportMapper.getInfoByCode(patient.getPatientcode(), "3");
            //生成状态：null未生成 0.生成中 1.已生成
            if (or == null || or.getCreateStatus() == null || or.getCreateStatus().intValue() != 1) {
                throw new ServiceException("体检号" + patient.getPatientcode() + "的临时报告未生成！");
            }
            String patientcode = patient.getPatientcode();
            //体检类型：0.健康体检 1.职业体检 2.综合 3.复查
            if (!"0".equals(patient.getIdExamtype()) && !"2".equals(patient.getIdExamtype())) {
                throw new ServiceException("体检号" + patientcode + "的体检类型不能终审交接！");
            }
            if (patient.getJktjzt() != null && (patient.getJktjzt() == 7 || patient.getJktjzt() > 8)) {
                throw new ServiceException("体检号" + patientcode + "已终审，无需终审交接！");
            }
            if (patient.getFReadytofinal() == null || patient.getFReadytofinal() != 1) {
                throw new ServiceException("体检号" + patientcode + "分检未完成，不能终审交接！");
            }
            //终审通过
            patient.setJktjzt(7);
            peispatientMapper.updateById(patient);

            //设置上传标示
            PeisState ps = peisStateService.setScbs(patient.getPatientcode(), 0);
            //收费项目上传标志
            ps.setIdGuidancereturnedby(0);
            peisStateService.updateById(ps);

            //报告
            Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                    .eq("patientcode", patientcode).eq("disease_health", 0));
            if (report == null) {
                report = new Report();
                report.setDiseaseHealth(0);
                report.setPatientcode(patientcode);
                report.setShortCode(ToolUtil.getShortCodeByLong(patientcode));
                report.setIsPrintMessage(0);
                report.setCreateNum(1);
                report.setUrlPdf(or.getPdfUrl());
            }
            //终审通过
            report.setStatus(7);
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
            //不是个检
            if (patient.getNumorgresv() != null && !"-1".equals(patient.getNumorgresv())) {
                report.setNumorgresv(patient.getNumorgresv());
            } else {
                report.setNumorgresv(null);
            }
            report.setSex(patient.getIdSex());
            //report.setUrlWord(patient.getHealthcard());
            report.setLastId(userId);
            report.setLastMan(userName);
            report.setLastTime(now);
//    		report.setTbbz(0);交接同步
            report.setGenerateName(or.getCreater());
            report.setGenerateDate(or.getCreateTime());
            report.setGenerateHint(null);
            report.setConfigId(or.getConfigId());
            if (report.getId() == null) {
                reportMapper.insert(report);
            } else {
                reportMapper.updateById(report);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 结案
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean updateclose(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException("结案失败：请选择一条要结案的记录！");
        }
        for (int i = 0; i < ids.size(); i++) {
            // 判断体检者表是否存在
            Peispatient peispatient = peispatientMapper.getInfoById(ids.get(i));
            if (null == peispatient) {
                //体检者历史
                Peispatienthistory peispatientHistory = peispatienthistoryMapper.getInForById(ids.get(i));
                if (null == peispatientHistory) {
                    throw new ServiceException("结案失败：第" + (i + 1) + "条记录不存在，已经被删除！");
                }
            } else {
                // 判断体检类型
                if (peispatient.getIdExamtype().equals("0")) {
                    // 报告领取状态
                    Peispatient bgPeispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("id", ids.get(i)).eq("jktjzt", 11));
                    if (null == bgPeispatient) {
                        throw new ServiceException("结案失败：第" + (i + 1) + "条记录 " + peispatient.getPatientname() + " 健康报告未领取，不可以结案！");
                    }
                } else if (peispatient.getIdExamtype().equals("1")) {
                    Peispatient bgPeispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("id", ids.get(i)).eq("zytjzt", 11));
                    if (null == bgPeispatient) {
                        throw new ServiceException("结案失败：第" + (i + 1) + "条记录 " + peispatient.getPatientname() + " 职业报告未领取，不可以结案！");
                    }
                } else {
                    Peispatient jkPeispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("id", ids.get(i)).eq("jktjzt", 11));
                    Peispatient bgPeispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("id", ids.get(i)).eq("zytjzt", 11));
                    if (null == jkPeispatient && null == bgPeispatient) {
                        throw new ServiceException("结案失败：第" + (i + 1) + "条记录 " + peispatient.getPatientname() + " 健康和职业报告未领取，不可以结案！");
                    } else if (null == jkPeispatient) {
                        throw new ServiceException("结案失败：第" + (i + 1) + "条记录 " + peispatient.getPatientname() + " 健康报告未领取，不可以结案！");
                    } else if (null == bgPeispatient) {
                        throw new ServiceException("结案失败：第" + (i + 1) + "条记录 " + peispatient.getPatientname() + " 职业报告未领取，不可以结案！");
                    }
                }
                Peispatient peispatientNew = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("id", ids.get(i)).eq("f_closed", 1));
                // 未结案
                if (null == peispatientNew) {
                    //结案
                    peispatient.setFClosed(1);
                    peispatient.setDateclosed(new Date());
                    // 更新实体类
                    peispatientMapper.updateById(peispatient);
                }
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 旧案召回
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reSaveHistory(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException("error@请选择一条要旧案召回的记录");
        }

        for (int i = 0, l = ids.size(); i < l; i++) {
            // 判断体检者表是否存在
            Peispatienthistory history = peispatienthistoryMapper.getInForById(ids.get(i));
            if (null == history) {
                throw new ServiceException("error@旧案召回失败：第" + (i + 1) + "条记录没有归档，不可以旧案召回！");
            }
        }

        //插入体检者表
        Integer i = peispatientMapper.reSaveHistory(ids);
        //删除体检者历史表
        if (i > 0) {
            peispatienthistoryMapper.deleteBatchIds(ids);
        }
        if (i == 0) {
            throw new ServiceException("error@旧案召回失败：系统发生异常，请联系管理员！");
        }

        return Boolean.TRUE;
    }


    /**
     * 加急
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean jiaji(List<String> ids) {
        List<Peispatient> patients = peispatientMapper.selectList(new QueryWrapper<Peispatient>().in("id", ids));

        for (Peispatient patient : patients) {
            //是否加急：0.否 1.是
            patient.setIsjj(1);
        }
        peispatientService.updateBatchById(patients);
        return Boolean.TRUE;
    }


    /**
     * 临时报告生成
     *
     * @param param
     * @return
     */
    @Override
    public Boolean createTemp(IReportParam param) {
        List<String> patientcodes = param.getPatientcode();
        if (CollectionUtils.isNotEmpty(patientcodes)) {
            for (int i = 0; i < patientcodes.size(); i++) {
                String code = patientcodes.get(i);
                Peispatient patient = peispatientMapper.getByPatientCode(code);
                if (patient == null) {
                    throw new ServiceException("无体检者记录:" + code, HttpStatus.SUCCESS);
                }
                //其他报告
                OtherReport or = otherReportMapper.selectOne(new QueryWrapper<OtherReport>()
                        .eq("patientcode", code).eq("report_type", 3));
                if (ObjectUtils.isEmpty(or)) {
                    or = new OtherReport();
                    or.setPatientcode(code);
                    or.setReportType(3);
                    or.setCreateStatus(1);
                    otherReportMapper.insert(or);
                }
                peispatientMapper.updateById(patient);
                // 只生成健康报告
                param.setLrctType(0);
                param.setType(1);
                param.setCode(code);
                CreateReportVo vo = null;
                try {
                    vo = iPersonalReportService.createReport(param);
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }

                //保存pdf地址
                or.setPdfUrl(vo.getReportPath());
                or.setCreateStatus(1);
                or.setCreater(SecurityUtils.getUsername());
                otherReportMapper.updateById(or);

                //生成的报告,插入到报告内容表中
                String jsonString = JSON.toJSONString(vo.getIReportVo());
                //把前缀都替换掉
                jsonString = jsonString.replace(vo.getPrefix(),"");
                reportContentService.createReportContent(jsonString, 3, code, String.valueOf(param.getDh()), null, null, null, vo.getReportPicList());

                // 插入redis同步报告
                SyncFileCheckDto dto = new SyncFileCheckDto(or.getId(),or.getPatientcode(),or.getPdfUrl(),new Date(),1,0);
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_CHECK, dto, DateUtil.currentSeconds());
            }

        } else {
            throw new ServiceException("请选择体检者:");
        }
        return Boolean.TRUE;

    }


    /**
     * 到检确认
     * @param param
     * @return
     */
    @Override
    public Boolean confirmOrder(ConfirmOrderParam param) {
        Peispatient patient = peispatientMapper.getInfoById(param.getId());
        Integer countreportoccupation = patient.getCountreportoccupation();
        if(countreportoccupation==null){
            throw new ServiceException("不是第三方平台预约体检者，不能导检确认。");
        }
        // TODO: 2023/10/21 待连接到平安接口 到检确认 发送请求
        Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
        String path = domain.getRsPfDomain() + Constants.ONLINE_CONFIRMORDER;
        OnlineConfirmParam onlineConfirmParam = new OnlineConfirmParam();
//        PingAnProperties pingAnProperties
        return Boolean.TRUE;
    }
}

