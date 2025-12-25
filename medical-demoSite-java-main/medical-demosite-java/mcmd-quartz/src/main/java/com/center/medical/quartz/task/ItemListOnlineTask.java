package com.center.medical.quartz.task;

import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.sellcrm.bean.vo.NeedNoticeVo;
import com.center.medical.sellcrm.service.ItemListOnlineService;
import com.center.medical.service.PeisOlService;
import com.center.medical.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 线上备单发送短信
 * 备单确认后自动通知体检码
 */
@Component("itemListOnlineTask")
public class ItemListOnlineTask {
    // TODO: ??? APP微信线上备单发送短信？
    private static final Logger log = LoggerFactory.getLogger(ItemListOnlineTask.class);
    @Resource
    private ItemListOnlineService itemListOnlineService;
    @Resource
    private PeisOlService peisOlService;
    @Resource
    private ISysConfigService iSysConfigService;

    public void send(){
        log.info("自动通知体检码线程开始--------------");
        List<NeedNoticeVo> wechatCodeDTOList = itemListOnlineService.selectNeedNoticeWechatCodeList();
        log.error("本次查询出记录："+wechatCodeDTOList.size()+"条。");
        for(NeedNoticeVo wechatCodeDTO:wechatCodeDTOList){
            try{
//                String smsResult= SDKTestSendTemplateSMS.sendMsg(wechatCodeDTO[8].toString(),"1202559",new String[]{wechatCodeDTO[4].toString()},"8a216da85a60ae8d015a6a6eaa7f058e");
                SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                String smsResult= SDKTestSendTemplateSMS.sendMsg(smsConfig,wechatCodeDTO.getWechatNoticeTime().toString()
                        ,"1206211",new String[]{wechatCodeDTO.getWechatCode()},"8a216da85a60ae8d015a6a6eaa7f058e");
                if("success".equals(smsResult)){
                    log.error("通知体检码发送成功，电话："+wechatCodeDTO.getPhone()+",体检号："+wechatCodeDTO.getPatientbizno());
                }else{
                    log.error("通知体检码发送失败，电话："+wechatCodeDTO.getPhone()+",错误信息:"+smsResult+",体检号："+wechatCodeDTO.getPatientbizno());
                }
                peisOlService.tagNoticeWechatCode(wechatCodeDTO.getId());
                log.error("通知体检码成功,体检号："+wechatCodeDTO.getPatientbizno());
            }catch(Exception e){
                log.error("通知体检码异常,体检号："+wechatCodeDTO.getPatientbizno(),e);
            }
        }
        log.error("自动通知体检码线程结束");
    }



}
