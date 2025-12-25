package com.center.medical.report.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.MD5;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.PeisStateMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.SmsRecordMapper;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.dao.ShortmessageMapper;
import com.center.medical.report.bean.dto.ChartDataDto;
import com.center.medical.report.bean.dto.GetReportDto;
import com.center.medical.report.bean.dto.HealthAssociateExportDto;
import com.center.medical.report.bean.model.ExportStatistics;
import com.center.medical.report.bean.model.Formdata;
import com.center.medical.report.bean.model.Griddata;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.GetReportUrlVo;
import com.center.medical.report.bean.vo.PhoneInformVo;
import com.center.medical.report.bean.vo.ReportRemindVo;
import com.center.medical.report.bean.vo.TotalAuditVo;
import com.center.medical.report.dao.ReportMapper;
import com.center.medical.report.service.ReportService;
import com.center.medical.reservation.bean.param.ReceivingReportsParam;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.SmsRecordService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BG报告主表(Report)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:14
 */
@Slf4j
@Service("reportService")
@RequiredArgsConstructor
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    private final ReportMapper reportMapper;
    private final SysBranchMapper sysBranchMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeisStateMapper peisStateMapper;
    private final SysUserMapper sysUserMapper;
    private final SmsRecordMapper smsRecordMapper;
    private final ShortmessageMapper shortmessageMapper;
    private final SmsRecordService smsRecordService;
    private final ISysBranchService iSysBranchService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final ISysConfigService iSysConfigService;

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Report> getPage(PageParam<Report> page, ReportParam param) {
        return reportMapper.getPage(page, param);
    }

    /**
     * 分页查询体检报告待领提醒
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportRemindVo> getReportRemindPage(PageParam<Report> page, ReportRemindParam param) {
        return reportMapper.getReportRemindPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Report getInfoById(String id) {
        return reportMapper.getInfoById(id);
    }

    @Override
    public Report getInfoByPatientcode(String patientcode, int diseaseHealth) {
        return reportMapper.getInfoByPatientcode(patientcode, diseaseHealth);
    }


    /**
     * 分页查询职业报告交接
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<Report> getPaPage(PageParam<Report> page, ProfessionAssociateParam param) {
        //没参数设置一下标识
        if (ObjectUtils.isEmpty(param)) {
            param.setFlag("true");
        } else {
            if (ObjectUtils.isEmpty(param.getAutoFill()) && ObjectUtils.isEmpty(param.getPatientcode()) && ObjectUtils.isEmpty(param.getIsHandover())
                    && ObjectUtils.isEmpty(param.getIsNotHandover()) && ObjectUtils.isEmpty(param.getNumorgresv()) && ObjectUtils.isEmpty(param.getStatus())
                    && ObjectUtils.isEmpty(param.getName()) && ObjectUtils.isEmpty(param.getJoinPersonId()) && ObjectUtils.isEmpty(param.getXsjlid())
            ) {
                param.setFlag("true");
            }
        }
        return reportMapper.getPaPage(page, param);
    }


    /**
     * 批量通过职业报告交接
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(FormdataAndGriddataParam param) throws Exception {
        List<Griddata> griddata = param.getGriddata();
        Formdata from = param.getFormdata();
        for (Griddata map : griddata) {
            Peispatient patient = peispatientMapper.getInfoById(map.getId());
            if (ObjectUtils.isNotEmpty(patient)) {
                //职业体检状态
                // 健康
                if ("0".equals(from.getDiseaseHealth())) {
                    if (ObjectUtils.isEmpty(patient.getJktjzt()) || patient.getJktjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,交接失败!");
                    }
                } else {
                    //职业
                    if (ObjectUtils.isEmpty(patient.getZytjzt()) || patient.getZytjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,交接失败!");
                    }
                }

                //更新近期体检者表报告状态
                patient.setIdInformway(map.getGrantId());
                if ("0".equals(from.getDiseaseHealth())) {
                    patient.setJktjzt(9);
                } else {
                    patient.setZytjzt(9);
                }

                setScbs(patient, 0);
                peispatientMapper.updateById(patient);

                Report report = reportMapper.selectOne(new QueryWrapper<Report>().eq("patientcode", map.getPatientcode())
                        .eq("disease_health", from.getDiseaseHealth()));
                if (ObjectUtils.isNotEmpty(report)) {
                    if (report.getStatus() >= 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已交接,交接失败!");
                    } else if (report.getStatus() != 7) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告尚未终审,交接失败!");
                    }
                    String revPersonId = ObjectUtils.isEmpty(from.getRevPersonId()) ? null : from.getRevPersonId().toString().trim();
                    /** 接受人ID */
                    report.setRevPersonId(revPersonId);
                    SysUser user1 = sysUserMapper.selectUserByUserNo(revPersonId);
                    if (ObjectUtils.isNotEmpty(user1)) {
                        String password = ObjectUtils.isEmpty(from.getPassword()) ? null : from.getPassword().toString().trim();
                        if (!MD5.encode(password).equals(user1.getReciveCode())) {
                            throw new ServiceException("承接人密码错误,交接失败!");
                        }
                        /** 接受人名称 */
                        report.setRevPersonMan(user1.getUserName());
                    } else {
                        throw new ServiceException("未找到承接人,交接失败!");
                    }

                    String joinPersonId = SecurityUtils.getUserNo();
                    /** 交接人ID */
                    report.setJoinPersonId(joinPersonId);
                    SysUser user = sysUserMapper.selectUserByUserNo(joinPersonId);

                    if (ObjectUtils.isNotEmpty(user)) {
                        /** 交接人名称 */
                        report.setJoinPersonMan(user.getUserName());
                    }

                    /** 交接时间 */
                    String revTime = from.getRevTime() == null ? null : from.getRevTime().toString().trim();
                    SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    report.setRevTime(fromat.parse(revTime));
                    /** 柜子号 */
                    report.setChestNum(map.getChestNum() == null ? "0" : map.getChestNum());
                    /** 发放方式 */
                    report.setGrantId(map.getGrantId());
                    /** 0:总检开始、1:总检完成、2:报告已打印、
                     * 3:报告一审通过、4:报告一审不通过、
                     * 5:二审通过、6:二审不通过、
                     * 7:终审通过、8:终审不通过、
                     * 9:报告已交接、10:报告已通知、11:报告已领取 */
                    report.setStatus(9);
                    report.setTbbz(0);//未同步
                    this.updateById(report);
                } else {
                    throw new Exception("报告中未找到体检号为" + map.getPatientcode() + ",名称为" + map.getPatientname() + "的体检者");
                }
            } else {
                throw new Exception("体检表中未找到体检号为" + map.getPatientcode() + ",名称为" + map.getPatientname() + "的体检者");
            }
        }

        return true;
    }


    /**
     * 设置体检者上传状态
     *
     * @param patient
     * @param scbs
     * @return
     */
    public PeisState setScbs(Peispatient patient, int scbs) {
        if (StringUtils.isEmpty(patient.getPatientcode())) {
            return null;
        }
        PeisState ps = peisStateMapper.selectOne(new QueryWrapper<PeisState>().eq("patientcode", patient.getPatientcode()));
        if (ps == null) {
            ps = new PeisState(patient.getPatientcode());
            ps.setScbs(scbs);
            peisStateMapper.insert(ps);
        } else {
            ps.setScbs(scbs);
            peisStateMapper.updateById(ps);
        }
        return ps;
    }


    /**
     * 职业报告交接反交接
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unaudit(FormdataAndGriddataParam param) {
        List<Griddata> griddata = param.getGriddata();
        Formdata from = param.getFormdata();
        for (Griddata map : griddata) {
            Peispatient patient = peispatientMapper.getInfoById(map.getId());
            if (ObjectUtils.isNotEmpty(patient)) {
                // 1职业
                if ("1".equals(param.getFormdata().getDiseaseHealth())) {
                    if (ObjectUtils.isEmpty(patient.getZytjzt()) || patient.getZytjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,反交接失败!");
                    }
                } else {
                    //0健康
                    if (ObjectUtils.isEmpty(patient.getJktjzt()) || patient.getJktjzt() < 1) {
                        throw new ServiceException("体检号:【" + patient.getPatientcode() + "】该体检者总检未完成,反交接失败!");
                    }
                }

                //更新近期体检者表报告状态
                if ("0".equals(from.getDiseaseHealth())) {
                    patient.setJktjzt(7);
                } else {
                    patient.setZytjzt(7);
                }
                setScbs(patient, 0);
                peispatientMapper.updateById(patient);

                Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                        .eq("patientcode", map.getPatientcode()).eq("disease_health", from.getDiseaseHealth()));

                if (ObjectUtils.isNotEmpty(report)) {
                    if (report.getStatus() > 10) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告已领取,反交接失败!");
                    } else if (report.getStatus() < 9) {
                        throw new ServiceException("体检号:【" + report.getPatientcode() + "】的报告尚未交接,反交接失败!");
                    }
                    String revPersonId = ObjectUtils.isEmpty(from.getRevPersonId()) ? null : from.getRevPersonId().toString().trim();
                    /** 接受人ID */
                    report.setRevPersonId(revPersonId);
                    SysUser user1 = sysUserMapper.selectUserByUserNo(revPersonId);

                    if (ObjectUtils.isNotEmpty(user1)) {
                        String password = ObjectUtils.isEmpty(from.getPassword()) ? null : from.getPassword().toString().trim();
                        if (!MD5.encode(password).equals(user1.getReciveCode())) {
                            throw new ServiceException("承接人密码错误,交接失败!");
                        }
                        /** 接受人名称 */
                        report.setRevPersonMan(user1.getUserName());
                    } else {
                        throw new ServiceException("未找到承接人,交接失败!");
                    }

                    String joinPersonId = SecurityUtils.getUserNo();
                    /** 交接人ID */
                    report.setJoinPersonId(joinPersonId);
                    SysUser user = sysUserMapper.selectUserByUserNo(joinPersonId);

                    if (ObjectUtils.isNotEmpty(user)) {
                        /** 交接人名称 */
                        report.setJoinPersonMan(user.getUserName());
                    }

                    /** 交接时间 */
                    String revTime = ObjectUtils.isEmpty(from.getRevTime()) ? null : from.getRevTime().toString().trim();
                    SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    try {
                        report.setFirstTime(fromat.parse(revTime));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
                    throw new ServiceException("报告中未找到体检号为" + map.getPatientcode() + ",名称为" + map.getPatientname() + "的体检者");
                }
            } else {
                throw new ServiceException("体检表中未找到体检号为" + map.getPatientcode() + ",名称为" + map.getPatientname() + "的体检者");
            }
        }

        return true;
    }


    /**
     * 批量编辑柜子号保存
     *
     * @param formdata
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveEdit(Formdata formdata) {
        List<String> ids = formdata.getIds();
        for (String id : ids) {
            Report report = reportMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(report)) {
                throw new ServiceException("保存失败，报告已不存在！");
            }
            if (report.getStatus().intValue() < 9) {
                throw new ServiceException("体检号为<font color='red'>" + report.getPatientcode() + "</font>的报告未交接，不能编辑!");
            }
            String chestNum = ObjectUtils.isEmpty(formdata.getChestNum()) ? null : formdata.getChestNum();
            report.setChestNum(chestNum);
            report.setGrantId(formdata.getGrantId());
            this.updateById(report);
        }
        return true;
    }


    /**
     * 不分页查询职业报告交接导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<Report> getPaList(ProfessionAssociateParam param) {
        return reportMapper.getPaList(param);
    }


    /**
     * 查询报告交接统计数据
     *
     * @param param
     * @param i
     * @return
     */
    @Override
    public List<ExportStatistics> exportStatistics(ProfessionAssociateParam param, int i) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime start = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(start);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime end = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(end);
        }
        List<ExportStatistics> exportStatistics = reportMapper.exportStatistics(param, i);
        for (int j = 0; j < exportStatistics.size(); j++) {
            ExportStatistics exportStatistics1 = exportStatistics.get(j);
            exportStatistics1.setRownum(j + 1);
        }
        return exportStatistics;
    }


    /**
     * 折线图数据
     *
     * @param diseaseHealth
     * @return
     */
    @Override
    public Map<String, Object> getChartData(int diseaseHealth) {
        //当前日期减6
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date time = c.getTime();
        //查询
        List<ChartDataDto> list = reportMapper.getChartData(sdf.format(time), diseaseHealth);

        Map<String, Object> isOverhandMap = new HashMap<String, Object>();
        Map<String, Object> isNotOverhandmap = new HashMap<String, Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        //数据存入map中
        for (int i = 0; i < list.size(); i++) {
            ChartDataDto os = list.get(i);
            String lastTime = os.getLastTime();
            isOverhandMap.put(lastTime, os.getStatus9());
            isNotOverhandmap.put(lastTime, os.getStatus7());
        }

        List<Object> isOverhandList = new ArrayList<Object>();
        List<Object> isNotOverhandList = new ArrayList<Object>();
        //循环七次
        for (int i = 0; i < 7; i++) {
            //每次循环日期加一天
            if (i != 0) {
                c.add(Calendar.DAY_OF_YEAR, 1);
            }
            String lastTime = sdf.format(c.getTime());
            //如果有数据就加进去，没有就写0
            if (ObjectUtils.isEmpty(isOverhandMap.get(lastTime))) {
                isOverhandList.add(0, "0");
            } else {
                isOverhandList.add(0, isOverhandMap.get(lastTime));
            }

            if (ObjectUtils.isEmpty(isNotOverhandmap.get(lastTime))) {
                isNotOverhandList.add(0, "0");
            } else {
                isNotOverhandList.add(0, isNotOverhandmap.get(lastTime));
            }
        }
        result.put("overhand", isOverhandList);
        result.put("notoverhand", isNotOverhandList);
        return result;
    }

    /**
     * 职业报告领取通知分页查询
     *
     * @param page
     * @param param
     * @param type
     * @param diseasehealth
     * @return
     */
    @Override
    public IPage<PhoneInformVo> getReceiveReportData(PageParam<PhoneInformVo> page, PhoneInformParam param, int type, int diseasehealth) {
        //交接时间
        if (ObjectUtils.isNotEmpty(param.getStartRevTime())) {
            DateTime start = DateUtil.beginOfDay(param.getStartRevTime());
            param.setStartRevTime(start);
        }
        if (ObjectUtils.isNotEmpty(param.getEndRevTime())) {
            DateTime end = DateUtil.endOfDay(param.getEndRevTime());
            param.setEndRevTime(end);
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }

        Integer notifyType = diseasehealth == 1 ? 1 : 2;
        param.setNotifyType(notifyType);
        IPage<PhoneInformVo> iPage = reportMapper.getReceiveReportData(page, param, type, diseasehealth);
        return iPage;
    }


    /**
     * 通知
     *
     * @param noticeParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean notice(NoticeParam noticeParam) {
        log.info("报告通知参数：{}", noticeParam);
        //取出参数
        List<String> ids = noticeParam.getIds();
        String notificationResult = noticeParam.getType();
        String notifyMemo = noticeParam.getNotifyMemo();
        int diseaseHealth = noticeParam.getDiseaseHealth();

        Date notifyDate = new Date();
        String notifyType = diseaseHealth == 1 ? "1" : "2";
        //循环ids
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            Report report = reportMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(report)) {
                throw new ServiceException("未找到报告数据，请刷新重试！");
            }
            //通知结果
            report.setNotificationResult(notificationResult);
            //覆盖上次备注
            report.setNotifyMemo(notifyMemo);
            //通知时间
            report.setNotifyDate(notifyDate);

            //体检者表
            Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", report.getPatientcode()));
            if (ObjectUtils.isEmpty(patient)) {
                throw new ServiceException("未找到体检者数据，请刷新重试！");
            }
            //通知类型
            if ("3".equals(notificationResult) || "5".equals(notificationResult)) {
                if (report.getStatus() == 9) {
                    report.setStatus(10);
                    if (diseaseHealth == 1) {
                        patient.setZytjzt(10);
                    } else {
                        patient.setJktjzt(10);
                    }
                }
            } else {
                if (report.getStatus() == 10) {
                    report.setStatus(9);
                    if (diseaseHealth == 1) {
                        patient.setZytjzt(9);
                    } else {
                        patient.setJktjzt(9);
                    }
                }
            }
            setScbs(patient, 0);
            //通知操作人
            report.setNoticer(SecurityUtils.getUsername());
            //更新状态
            reportMapper.updateById(report);
            peispatientMapper.updateById(patient);
            smsRecordMapper.delete(new QueryWrapper<SmsRecord>().eq("patientcode", patient.getPatientcode())
                    .eq("notify_type", notifyType).eq("notify_result", 2));
        }
        return Boolean.TRUE;
    }


    /**
     * 保存短信
     *
     * @param map
     * @param diseaseHealth
     * @return
     */
    @Override
    public String sendMsg(SendMsgFormDataParam map, int diseaseHealth) {
        //报告ids
        List<String> ids = map.getIds();
        //模板ID
        String idTem = map.getMessageName();
        String notifyType = diseaseHealth == 1 ? "1" : "2";
        Shortmessage message = shortmessageMapper.getInfoById(idTem);
        if (ObjectUtils.isEmpty(message)) {
            throw new ServiceException("保存失败，短信模板已被删除！");
        }

        //一个月内不能重复发送
        for (String id : ids) {
            Report report = reportMapper.getInfoById(id);
            String patientcode = report.getPatientcode();
            Long count = smsRecordMapper.selectCount(new QueryWrapper<SmsRecord>()
                    .eq("notify_type", notifyType)
                    .eq("patientcode", patientcode)
                    .in("notify_result", 2,3));
            if (count>0){
                throw new ServiceException("本月已通知"+patientcode+"领取报告，30天内不允许二次通知");
            }

        }

        //网上模板ID
        String idTemplate = message.getTemplateId();
        //通知备注
        String notifyMemo = map.getNotifyMemo();
        //是否立即发送
        Integer isImmediately = "true".equals(map.getImmediately()) ? 1 : 0;
        String sendTimeStr = map.getSendTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //通知时间
        Date notifyTime = null;
        try {
            notifyTime = (StringUtils.isEmpty(sendTimeStr) || isImmediately.intValue() == 1) ? new Date() : sdf.parse(sendTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "success";
        String creater = SecurityUtils.getUsername();
        //appID
        String appId = message.getAppid();
        String messageText = message.getMessageText();
        String notifyContent = ToolUtil.getSmsContent(messageText, null);
        //立即发送
        if (isImmediately.intValue() == 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                Report report = reportMapper.getInfoById(id);
                boolean isSend = false;
                if (ObjectUtils.isEmpty(report)) {
                    builder.append("第" + (i + 1) + "条未找到报告数据，发送失败！");
                    continue;
                }
                //体检号
                String patientcode = report.getPatientcode();
                Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                        .eq("patientcode", report.getPatientcode()));
                if (ObjectUtils.isEmpty(patient)) {
                    builder.append("体检号" + patientcode + "未找到体检者数据，发送失败！");
                    continue;
                }
                String phone = report.getPhone();
                if (ObjectUtils.isEmpty(phone)) {
                    builder.append("体检号" + patientcode + "电话为空，发送失败！");
                    continue;
                }
                SmsRecord sms = smsRecordMapper.selectOne(new QueryWrapper<SmsRecord>()
                        .eq("notify_type", notifyType).eq("patientcode", patientcode).eq("notify_result", 2));
                if (ObjectUtils.isEmpty(sms)) {
                    sms = new SmsRecord();
                    sms.setNotifyType(notifyType);
                    sms.setPatientcode(patientcode);
                }
                sms.setCreater(creater);
                sms.setIsImmediately(isImmediately);
                sms.setIdTemplate(idTem);
                sms.setNotifyTime(notifyTime);
                sms.setNotifyContent(notifyContent);
                sms.setNotifyResult(2);
                try {
                    SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                    String result_one = SDKTestSendTemplateSMS.sendMsg(smsConfig,phone, idTemplate, null, appId);
                    if (!"success".equals(result_one)) {
                        builder.append("体检号" + patientcode + "<font color='red'>发送失败！</font>," + result_one);
                        sms.setNotifyResult(0);
                    } else {
                        builder.append("体检号" + patientcode + "<font color='green'>发送成功！</font>");
                        sms.setNotifyResult(3);
                        isSend = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    builder.append("体检号" + patientcode + "<font color='red'>发送失败！</font>," + e.getMessage());
                    sms.setNotifyResult(0);
                }
                report.setNoticer(SecurityUtils.getUsername());
                report.setNotifyMemo(report.getNotifyMemo() == null ? notifyMemo : (report.getNotifyMemo() + "\n" + notifyMemo));
                if (report.getStatus() == 9 && isSend) {
                    report.setStatus(10);
                    report.setNotificationResult("4");
                    report.setNotifyDate(notifyTime);
                    if (diseaseHealth == 1) {
                        patient.setZytjzt(10);
                    } else {
                        patient.setJktjzt(10);
                    }
                    setScbs(patient, 0);
                }
                builder.append("</br>");
                smsRecordService.saveOrUpdate(sms);
                reportMapper.updateById(report);
                peispatientMapper.updateById(patient);
            }
            result = builder.toString();
        } else {//定时发送
            for (int i = 0; i < ids.size(); i++) {
                String id = ids.get(i);
                Report report = reportMapper.getInfoById(id);
                if (ObjectUtils.isEmpty(report)) {
                    throw new ServiceException("未找到报告数据，请刷新重试！");
                }
                String patientcode = report.getPatientcode();
                report.setNotifyDate(notifyTime);
                report.setNoticer(SecurityUtils.getUsername());
                report.setNotifyMemo(report.getNotifyMemo() == null ? notifyMemo : (report.getNotifyMemo() + "\n" + notifyMemo));
                reportMapper.updateById(report);

                SmsRecord sms = smsRecordMapper.selectOne(new QueryWrapper<SmsRecord>()
                        .eq("notify_type", notifyType).eq("patientcode", patientcode).eq("notify_result", 2));
                if (ObjectUtils.isEmpty(sms)) {
                    sms = new SmsRecord();
                    sms.setNotifyType(notifyType);
                    sms.setPatientcode(patientcode);
                }
                sms.setCreater(creater);
                sms.setIsImmediately(isImmediately);
                sms.setIdTemplate(idTem);
                sms.setNotifyTime(notifyTime);
                sms.setNotifyResult(2);
                sms.setNotifyContent(notifyContent);
                smsRecordService.saveOrUpdate(sms);
            }
        }
        return result;
    }


    /**
     * 取消发送
     *
     * @param patientcodes
     * @param diseaseHealth
     * @return
     */
    @Override
    public Boolean cancelSMS(List<String> patientcodes, int diseaseHealth) {
        String notifyType = diseaseHealth == 1 ? "1" : "2";
        for (String patientcode : patientcodes) {
            SmsRecord record = smsRecordMapper.selectOne(new QueryWrapper<SmsRecord>().eq("notify_type", notifyType)
                    .eq("patientcode", patientcode).eq("notify_result", 2));
            if (ObjectUtils.isEmpty(record)) {
                throw new ServiceException("体检号为" + patientcode + "的短信通知已发送或已被取消，取消失败！");
            }
            record.setNotifyResult(1);
            smsRecordMapper.updateById(record);
        }
        return true;
    }


    /**
     * 获取职业报告领取通知导出数据
     *
     * @param param
     * @param dh
     * @return
     */
    @Override
    public List<PhoneInformVo> exportNoticeReport(PhoneInformParam param, int dh) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartRevTime())) {
            DateTime start = DateUtil.beginOfDay(param.getStartRevTime());
            param.setStartRevTime(start);
        }
        if (ObjectUtils.isNotEmpty(param.getEndRevTime())) {
            DateTime end = DateUtil.endOfDay(param.getEndRevTime());
            param.setEndRevTime(end);
        }
        List<PhoneInformVo> list = reportMapper.exportNoticeReport(param, dh);
        for (int i = 0; i < list.size(); i++) {
            PhoneInformVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }


    /**
     * 领取
     *
     * @param map
     * @param diseaseHealth
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean receive(ReceiveFromDataParam map, int diseaseHealth) {
        log.info("报告领取参数：{}",map);
        log.info("报告领取diseaseHealth：{}",diseaseHealth);

        List<String> ids = map.getIds();
        String getterName = map.getGetterName();
        Date getTime = ObjectUtils.isEmpty(map.getGetTime()) ? new Date() : map.getGetTime();
        String getterPhone = map.getGetterPhone();
        String grantId = map.getGrantId();
        String expressNum = map.getExpressNum();
        String expressId = map.getExpressId();
        String issueId = SecurityUtils.getUserNo();

        for (int i = 0; i < ids.size(); i++) {
            Report report = reportMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isEmpty(report)) {
                throw new ServiceException("未找到报告数据，领取失败！");
            }
            String patientCode = report.getPatientcode();
            Integer status = report.getStatus();
            if (ObjectUtils.isEmpty(status) || status < 9) {
                throw new ServiceException("体检号为" + patientCode + "的报告未交接，领取失败！");
            } else if (status == 11) {
                throw new ServiceException("体检号为" + patientCode + "的报告已领取，领取失败！");
            }

            Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", patientCode));
            if (ObjectUtils.isEmpty(patient)) {
                throw new ServiceException("未找到体检号为" + patientCode + "的体检者数据，请刷新重试！");
            }
            //1职业，0健康
            if (diseaseHealth == 1) {
                if (ObjectUtils.isEmpty(patient.getZytjzt()) || patient.getZytjzt() < 9) {
                    throw new ServiceException("体检号为" + patientCode + "的报告未交接，领取失败！");
                } else if (patient.getZytjzt() == 11) {
                    throw new ServiceException("体检号为" + patientCode + "的报告已领取，领取失败！");
                }
                patient.setZytjzt(11);
            } else {
                if (ObjectUtils.isEmpty(patient.getJktjzt()) || patient.getJktjzt() < 9) {
                    throw new ServiceException("体检号为" + patientCode + "的报告未交接，领取失败！");
                } else if (patient.getJktjzt() == 11) {
                    throw new ServiceException("体检号为" + patientCode + "的报告已领取，领取失败！");
                }
                patient.setJktjzt(11);
            }
            setScbs(patient, 0);
            report.setGetterName(getterName);
            report.setExpressId(expressId);
            report.setExpressNum(expressNum);
            report.setGetterPhone(getterPhone);
            report.setGrantId(grantId);
            report.setStatus(11);
            report.setGetTime(getTime);
            report.setIssueId(issueId);
            //签名图片
            report.setSignUrl(map.getSignUrl());
            reportMapper.updateById(report);
            peispatientMapper.updateById(patient);
        }
        return Boolean.TRUE;
    }

    /**
     * 反领取
     *
     * @param ids
     * @param diseaseHealth
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unNotice(List<String> ids, int diseaseHealth) {
        for (int i = 0; i < ids.size(); i++) {
            Report report = reportMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isEmpty(report)) {
                throw new ServiceException("未找到报告数据，领取失败！");
            }
            String patientCode = report.getPatientcode();
            Integer status = report.getStatus();
            if (ObjectUtils.isEmpty(status) || status != 11) {
                throw new ServiceException("体检号为" + patientCode + "的报告未领取，反领取失败！");
            }
            Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
            if (ObjectUtils.isEmpty(patient)) {
                throw new ServiceException("未找到体检号为" + patientCode + "的体检者数据，请刷新重试！");
            }
            if (diseaseHealth == 1) {
                if (patient.getZytjzt() == null || patient.getZytjzt() != 11) {
                    throw new ServiceException("体检号为" + patientCode + "的报告未领取，反领取失败！");
                }
                //通知结果
                if ("3".equals(report.getNotificationResult()) || "4".equals(report.getNotificationResult())) {
                    report.setStatus(10);
                    patient.setZytjzt(10);
                } else {
                    report.setStatus(9);
                    patient.setZytjzt(9);
                }
            } else {
                if (ObjectUtils.isEmpty(patient.getJktjzt()) || patient.getJktjzt() != 11) {
                    throw new ServiceException("体检号为" + patientCode + "的报告未领取，反领取失败！");
                }
                if ("3".equals(report.getNotificationResult()) || "4".equals(report.getNotificationResult())) {
                    report.setStatus(10);
                    patient.setJktjzt(10);
                } else {
                    report.setStatus(9);
                    patient.setJktjzt(9);
                }
            }
            report.setIssueId(null);
            setScbs(patient, 0);
            reportMapper.updateById(report);
            peispatientMapper.updateById(patient);
        }
        return Boolean.TRUE;
    }


    /**
     * 获取导出职业报告领取数据
     *
     * @param param
     * @param dh
     * @return
     */
    @Override
    public List<GetReportDto> exportGetReport(PhoneInformParam param, int dh) {
        //交接时间
        if (ObjectUtils.isNotEmpty(param.getStartRevTime())) {
            DateTime start = DateUtil.beginOfDay(param.getStartRevTime());
            param.setStartRevTime(start);
        }
        if (ObjectUtils.isNotEmpty(param.getEndRevTime())) {
            DateTime end = DateUtil.endOfDay(param.getEndRevTime());
            param.setEndRevTime(end);
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        List<GetReportDto> list = reportMapper.exportGetReport(param, dh);
        return list;
    }


    /**
     * 分页查询报告审核统计
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public List<TotalAuditVo> getTotalAuditPage(PageParam<Report> page, TotalAuditParam param) {
        //设置开始时间和结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime dateTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(dateTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime end = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(end);
        }
        //获取一审、二审、三审数据
        List<TotalAuditVo> first = reportMapper.getFirst(page, param);
        List<TotalAuditVo> Second = reportMapper.getSecond(page, param);
        List<TotalAuditVo> last = reportMapper.getLast(page, param);


        //集合拼接
        first.addAll(Second);
        first.addAll(last);
        return first;
    }

    /**
     * 获取报告未出通知
     * @return
     */
    @Override
    public Long getReportNotReleasedCount() {
        return reportMapper.getReportNotReleasedCount();
    }

    /**
     * 获取未总检的数量
     * @return
     */
    @Override
    public Long getNotTotalInspectionCount() {
        return reportMapper.getNotTotalInspectionCount();
    }

    /**
     * 获取报告未出数量
     * @param num
     * @return
     */
    @Override
    public String getReportNotReleasedTime(String num) {
        return reportMapper.getReportNotReleasedTime(num);
    }

    /**
     * 获取报告url
     * @param param
     * @return
     */
    @Override
    public GetReportUrlVo getReportUrl(GetReportUrlParam param) {
        param.setPatientcode(ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null)));
        GetReportUrlVo vo = reportMapper.getReportUrl(param);
        if (ObjectUtils.isEmpty(vo)){
            throw new ServiceException("报告不存在！");
        }
        return vo;
    }


    /**
     * 接收报告
     * @param param
     * @return
     */
    @Override
    public Boolean receivingReports(ReceivingReportsParam param) {
        //校验授权码

        //校验数据
        Peispatient peispatient = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, param.getPatientcode()));
        if (ObjectUtils.isEmpty(peispatient)) throw new ServiceException("体检号不存在!");
        Report report = reportMapper.selectOne(new LambdaQueryWrapper<Report>()
                .eq(Report::getPatientcode, param.getPatientcode())
                .eq(Report::getDiseaseHealth, 0));
        if (ObjectUtils.isEmpty(report)) throw new ServiceException("报告不存在!");

        //上传
        MultipartFile file = param.getFile();
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.RiLin.value());
        Attachment attachment = new Attachment();
        String extName = FileUtil.extName(file.getOriginalFilename());
        attachment.setFileType("瑞林萨尔报告");
        attachment.setPatientcode(peispatient.getPatientcode());
        attachment.setType(AttachmentType.FILE.value());
        attachment.setBranchId(peispatient.getHospitalcode());
        attachment.setCreatedate(new Date());
        try {
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServiceException("接收报告失败！");
        }
        log.info("接收报告成功,体检号：{}", attachment.getId(), attachment);

        //保存报告
        report.setUrlWord(attachment.getFilePath());
        reportMapper.updateById(report);
        return Boolean.TRUE;
    }


    /**
     * 报告交接导出数据
     * @param param
     * @return
     */
    @Override
    public List<HealthAssociateExportDto> healthAssociateExport(ProfessionAssociateParam param) {
        List<HealthAssociateExportDto> list = reportMapper.healthAssociateExport(param);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setRownum(i + 1);
        }
        return list;
    }


    /**
     * 获取报告地址
     * @param patientcodes
     * @param diseaseHealth
     * @return
     */
    @Override
    public List<String> getReportAddress(List<String> patientcodes, Integer diseaseHealth) {
        Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
        String prefix = ZhongkangConfig.isOnline() ? domain.getRsPfDomain() : domain.getRsLcDomain();
        List<String> reportAddress = reportMapper.getReportAddress(patientcodes, diseaseHealth);
        return reportAddress.stream()
                .map(address -> prefix + address)
                .collect(Collectors.toList());
    }
}

