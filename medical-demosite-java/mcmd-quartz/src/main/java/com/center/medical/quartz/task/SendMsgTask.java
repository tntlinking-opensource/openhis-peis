package com.center.medical.quartz.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.PeisStateMapper;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.service.ShortmessageService;
import com.center.medical.member.bean.model.Memberbirthdat;
import com.center.medical.member.service.MemberbirthdatService;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.service.SmsRecordService;
import com.center.medical.system.service.ISysConfigService;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 定时发送短信定时任务
 */
@Component("SendMsgTask")
@DisallowConcurrentExecution
public class SendMsgTask {
    private static final Logger log = LoggerFactory.getLogger(SendMsgTask.class);
    @Resource
    private SmsRecordService smsRecordService;
    @Resource
    private PeispatientarchiveService peispatientarchiveService;
    @Resource
    private PeispatientService peispatientService;
    @Resource
    private ShortmessageService shortmessageService;
    @Resource
    private ReportService reportService;
    @Resource
    private PeisStateMapper peisStateMapper;
    @Resource
    private MemberbirthdatService memberbirthdatService;
    @Resource
    private ISysConfigService iSysConfigService;


    /**
     * 定时发送短信定时任务
     */
    public void sendTiming(Integer duringTime) {
        log.info("定时发送短信定时任务执行中--------------");
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(12L, ips)) {
            /*修改RECORD*/
            Date now = new Date();
            List<SmsRecord> records = smsRecordService.list(new QueryWrapper<SmsRecord>()
                    .eq("notify_result", 2)//等待状态
                    .in("notify_type", new String[]{"0", "1", "2", "3", "6", "7"})
                    .le("notify_time", now));
            for (SmsRecord record : records) {
                Date now1 = new Date();
                if (now1.after(offset)) {
                    log.info("定时发送短信定时任务，提前结束，超时了");
                    break;
                }
                System.out.println("发送短信体检号：" + record.getPatientcode());
                boolean canSend = true;
                String patientcode = record.getPatientcode();
                String archiveId = record.getArchiveId();
                Peispatient patient = null;
                String phone = null;
                Peispatientarchive archive = null;
                if ("3".equals(record.getNotifyType())) {
                    archive = peispatientarchiveService.getInfoById(archiveId);
                    if (archive == null) {
                        canSend = false;
                    } else {
                        phone = archive.getPhone();
                    }
                } else {
                    patient = peispatientService.getByPatientCode(patientcode);
                    if (patient == null) {
                        canSend = false;
                    } else {
                        phone = patient.getPhone();
                    }
                }
                String idTem = record.getIdTemplate();
                Shortmessage message = shortmessageService.getInfoById(idTem);//模板
                if (message == null) {
                    canSend = false;
                }
                String messageText = message.getMessageText();//短信正文
                boolean isSend = false;//发送结果
                if (canSend) {
                    String[] paramNames = message.getParams() == null ? null : message.getParams().split(",");
                    String[] params = paramNames == null ? null : new String[paramNames.length];
                    String result;
                    try {
                        SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                        result = SDKTestSendTemplateSMS.sendMsg(smsConfig,phone, message.getTemplateId(), params, message.getAppid());
                    } catch (Exception e) {
                        result = e.getMessage();
                        e.printStackTrace();
                    }
                    if ("success".equals(result)) {
                        isSend = true;
                    }
                }
                System.out.println("发送短信结果：" + isSend);
                record.setNotifyContent(message.getMessageText());//TODO 发送内容
                record.setNotifyResult(isSend ? 3 : 0);//通知结果
                String notifyType = record.getNotifyType();
                /*预检人员通知*/
                if ("0".equals(notifyType)) {
                    if (isSend && (patient.getIsNoticed() == null || patient.getIsNoticed().intValue() == 0)) {
                        patient.setIsNoticed(1);
                        peispatientService.updateById(patient);
                    }
                    /*报告领取通知*/
                } else if ("1".equals(notifyType) || "2".equals(notifyType)) {
                    int diseaseHealth = "1".equals(notifyType) ? 1 : 0;
                    Report report = reportService.getOne(new QueryWrapper<Report>()
                            .eq("patientcode", patientcode)
                            .eq("disease_health", diseaseHealth));
                    if (report != null) {
                        if (report.getStatus() == 9 && isSend) {
                            report.setStatus(10);
                            report.setNotificationResult("4");
                            if (diseaseHealth == 1) {
                                patient.setZytjzt(10);
                            } else {
                                patient.setJktjzt(10);
                            }
                            setScbs(patient, 0);
                            peispatientService.updateById(patient);
                        }
                        reportService.updateById(report);
                    }
                    //客户满意度回访
                } else if ("3".equals(notifyType)) {
                    if (isSend) {
                        archive.setRestatus(1);
                        peispatientarchiveService.updateById(archive);
//					if(get(Memberbirthdat.class, Restrictions.eq("hyId",archiveId))==null){
                        Memberbirthdat mb = new Memberbirthdat();
                        mb.setHyId(archiveId);
                        mb.setVisitStatus(0);//已回访
                        mb.setVisitMan(record.getCreater());//回访人
                        mb.setVisitTime(now);// 回访日期，当前日期
                        mb.setVisitType("短信回访");
                        mb.setVisitNote(messageText);
                        mb.setVisitText("0");// 跟进内容
                        memberbirthdatService.save(mb);
//					}
                    }
                }

                smsRecordService.updateById(record);

            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public PeisState setScbs(Peispatient patient, int scbs) {
        if (StringUtils.isEmpty(patient.getPatientcode())) {
            return null;
        }
        //体检者上传状态
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
}
