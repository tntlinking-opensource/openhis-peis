package com.center.medical.finance.listener;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.event.AppointmentSMSEvent;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.bean.param.AppointmentSMSParam;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.SmsRecordMapper;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.dao.ShortmessageMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通联支付 扫码支付
 */
@Slf4j
@Component("appointmentSMSListener")
@AllArgsConstructor
public class AppointmentSMSListener {

    private final SmsRecordMapper smsRecordMapper;
    private final PeispatientMapper peispatientMapper;
    private final ShortmessageMapper shortmessageMapper;
    private final ISysConfigService iSysConfigService;


    @Async
    @EventListener(AppointmentSMSEvent.class)
    public void appointmentSMS(AppointmentSMSEvent event) throws Exception {
        log.info("发送预约短信：{}", JSONUtil.toJsonStr(event));
        AppointmentSMSParam param = event.getParam();
        String patientcode = param.getPatientcode();
        //1.查询是否发送过预约短信,发送过就跳过
        List<SmsRecord> records = smsRecordMapper.selectList(new QueryWrapper<SmsRecord>()
                .eq("patientcode", patientcode)
                .eq("notify_type", 0));

        if (CollectionUtil.isEmpty(records)){
            //2.生成预约小程序链接
//            String url = generateUrllink();
//            System.out.println("打印一下生成跳小程序的连接:"+url);
            //2025-11-25 不用生成小程序的链接了,直接用沃德问卷的链接
            String url = "https://zkjkgl.world.com:9091/jqwj/#/custom/wx/login/65";
            //3.判断会员类型，选择模板立即发送
            String idTem = "";
            List<String> data = new ArrayList<>();
            if (StringUtils.isNotEmpty(param.getIdPatientclass())){
                if ("1".equals(param.getIdPatientclass())){
                    System.out.println("发送普通会员");
                    idTem = Constants.ADVANCE_NOTICE_ORDINARY_TEMPLATE;
                    data.add(url+" ");
                }else {
                    System.out.println("vip及贵宾");
                    idTem = Constants.ADVANCE_NOTICE_VIP_TEMPLATE;
                    data.add(param.getPatientname());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // 定义日期格式
                    data.add(sdf.format(param.getDateguidancereturned()));
                    data.add(url+" ");
                }
                String[] strings = data.toArray(new String[0]);

                //4.发送短信
                Shortmessage message = shortmessageMapper.getInfoById(idTem);
                String appId = message.getAppid();//appid
                String idTemplate = message.getTemplateId();//模板id
                String messageType = message.getMessageType();
                String messageText = message.getMessageText();
                String notifyContent = ToolUtil.getSmsContent(messageText, strings);

                //生成发送记录
                SmsRecord record = new SmsRecord(messageType, patientcode);
                record.setCreater("自动发送预约短信");
                record.setIsImmediately(1);
                record.setIdTemplate(idTem);
                record.setNotifyTime(new Date());
                record.setNotifyContent(notifyContent);
                Boolean isSuccess = false;
                try {
                    SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                    String result_one = SDKTestSendTemplateSMS.sendMsg(smsConfig,param.getPhone(), idTemplate, strings, appId);
                    if (!"success".equals(result_one)) {
                        System.out.println("体检号" + patientcode + "发送失败！," + result_one);
                    } else {
                        System.out.println("体检号" + patientcode + "发送成功！");
                        isSuccess = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("体检号" + patientcode + "发送失败 ," + e.getMessage());
                }
                if (isSuccess) {
                    record.setNotifyResult(3);
                } else {
                    record.setNotifyResult(0);
                }
                smsRecordMapper.insert(record);
            }


        }

        log.info("体检号"+patientcode+"发送预约短信成功!");


    }




    /**
     * 微信订阅短链接消息
     * @param
     * @param
     * @return
     */
    public String generateUrllink(){
        Domain domain = iSysConfigService.getDomain();
        String url = domain.getAppDomain()+"/appArticle/getJumpUrl";
//        String url = "http://localhost:8094"+"/appArticle/getJumpUrl";
        String str = HttpUtils.sendPost(url, "");
        log.info("generateUrllink获取的url是:{}",str);
        return str;
    }

}
