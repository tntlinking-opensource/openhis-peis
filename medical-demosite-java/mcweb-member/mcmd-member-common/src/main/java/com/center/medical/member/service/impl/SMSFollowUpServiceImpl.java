package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.SmsRecordMapper;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.dao.ShortmessageMapper;
import com.center.medical.member.bean.param.SMSFollowUpParam;
import com.center.medical.member.bean.param.SMSSaOrUpParam;
import com.center.medical.member.bean.param.SmsDataParam;
import com.center.medical.member.bean.param.SmssendParam;
import com.center.medical.member.bean.vo.SMSFollowUpVo;
import com.center.medical.member.dao.SMSFollowUpMapper;
import com.center.medical.member.service.SMSFollowUpService;
import com.center.medical.service.SmsRecordService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
@Slf4j
@Service("smsFollowUpService")
@RequiredArgsConstructor
public class SMSFollowUpServiceImpl extends ServiceImpl<SMSFollowUpMapper, Peispatient> implements SMSFollowUpService {

    private final SMSFollowUpMapper smsFollowUpMapper;
    private final SmsRecordMapper smsRecordMapper;
    private final ShortmessageMapper shortmessageMapper;
    private final PeispatientMapper peispatientMapper;
    private final SmsRecordService smsRecordService;
    private final ISysConfigService iSysConfigService;

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param Peispatient查询参数
    * @return 分页数据
    */
    @Override
    public IPage<SMSFollowUpVo> getList(PageParam<SMSFollowUpVo> page, SMSFollowUpParam param) {
        //设置属性
        param.setType(0);
        if(ObjectUtils.isEmpty(param.getIsReg())) {
            param.setIsReg(0);
        }
        //医师是当前用户
        if (1 == param.getIsReg()){
            param.setUserNo(SecurityUtils.getUserNo());
            //今天的开始和结束时间
            param.setBeginDay(DateUtil.beginOfDay(new Date()));
            param.setEndDay(DateUtil.endOfDay(new Date()));
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        param.setUserNo(SecurityUtils.getUserNo());
        return smsFollowUpMapper.getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public Peispatient getInfoById(String id){
        return smsFollowUpMapper.getInfoById(id);
    };


    /**
     * 查看短信数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SmsRecord> getSmsData(PageParam<SmsRecord> page, SmsDataParam param) {
        //设置条件
        QueryWrapper<SmsRecord> con = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param.getNotifyType())){
            con.eq("notify_type",param.getNotifyType());
        }
        if(StringUtils.isNotEmpty(param.getPatientcode())){
            con.eq("patientcode",param.getPatientcode());
        }
        if(StringUtils.isNotEmpty(param.getArchiveId())){
            con.eq("archive_id",param.getArchiveId());
        }
        //分页查询
        PageParam<SmsRecord> pager = smsRecordMapper.selectPage(page, con.orderByDesc("notify_time"));
        return pager;
    }

    /**
     * 查看短信数据
     * @param param
     * @return
     */
    @Override
    public List<HashMap<String, Object>> smssend(SmssendParam param) {
        List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        //取出属性
        List<String> idsArray = param.getIds();
        List<String> pCodesArray = param.getPatientcode();
        List<String> namesArray = param.getPatientname();

        HashMap<String, Object> mapParent = new HashMap<String, Object>();
        mapParent.put("id", "-1");
        mapParent.put("name", "全部");
        dataList.add(mapParent);
        //循环id
        for (int i = 0, l = idsArray.size(); i < l; i++) {
            String patientCode = pCodesArray.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", idsArray.get(i));
            map.put("pid", "-1");
            map.put("code",patientCode);
            // 短信发送记录
            SmsRecord record = smsRecordMapper.selectOne(new QueryWrapper<SmsRecord>()
                    .eq("patientcode",patientCode)
                    .eq("notify_type","0"));
            //有发送记录
            if(ObjectUtils.isNotEmpty(record)){
                HashMap<String,Object> record_map = new HashMap<String, Object>();
                record_map.put("immediately", record.getIsImmediately());
                record_map.put("sendTime", record.getNotifyTime());
                //短信信息表
                Shortmessage msg = shortmessageMapper.getInfoById(record.getIdTemplate());
                if(msg!=null){
                    record_map.put("idTemplate", record.getIdTemplate());
                    record_map.put("modelName", msg.getMessageText());
                    record_map.put("messageName", msg.getMessageName());
                }
                map.put("record", record_map);
                map.put("name", patientCode+ namesArray.get(i));
            }else{
                map.put("name", patientCode + namesArray.get(i));
            }
            dataList.add(map);
        }

        return dataList;
    }


    /**
     * 取消发送
     * @param patientcodes
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean cancleSMS(List<String> patientcodes) {
        //循环
        for(String patientcode:patientcodes){
            //短信发送记录
            SmsRecord record = smsRecordMapper.selectOne(new QueryWrapper<SmsRecord>()
                            .eq("notify_type","0")
                            .eq("patientcode", patientcode)
                            .eq("notify_result", 2));
            if(ObjectUtils.isEmpty(record)){
                continue;
            }
            //设置通知结果状态
            record.setNotifyResult(1);
            //更新
            smsRecordMapper.updateById(record);
        }
        return Boolean.TRUE;
    }


    /**
     * 群发短信编辑-保存
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean saveGroupData(SMSSaOrUpParam param) {

        List<String> patientCodes = param.getPatientcode();
        String idTem = param.getIdTemplate();
        //短信信息表
        Shortmessage message = shortmessageMapper.getInfoById(idTem);
        if(ObjectUtils.isEmpty(message)){
            throw new ServiceException( "保存失败，短信模板已被删除！");
        }
        //appid
        String appId = message.getAppid();
        //模板id
        String idTemplate=message.getTemplateId();
        //是否立即发送
        Integer isImmediately = "true".equals(param.getImmediately())?1:0;
        Date sendTimeStr = param.getSendTime();
        //通知时间
        Date notifyTime=(ObjectUtils.isEmpty(sendTimeStr)||isImmediately.intValue()==1)?new Date():sendTimeStr;
        String result = "success";
        //操作人
        String creater = SecurityUtils.getUsername();
        String messageText=message.getMessageText();
        //替換内容
        String notifyContent = getSmsContent(messageText, null);
        //立即发送
        if(isImmediately.intValue()==1){

            StringBuilder builder = new StringBuilder();
            boolean isSuccess = false;
            for(String patientCode:patientCodes){
                //短信发送记录
                List<SmsRecord> records = smsRecordMapper.selectList(new QueryWrapper<SmsRecord>()
                                .orderByDesc("createDate").eq("patientcode",patientCode)
                                .eq("notify_type","0").eq("notify_result",2));//等待通知 可以修改
                SmsRecord record=null;
                //没有就创建一个，有的话取第一个
                if(records.size()==0){
                    record = new SmsRecord("0", patientCode);
                }else{
                    record=records.get(0);
                }
                record.setCreater(creater);
                //是否立即发送
                record.setIsImmediately(isImmediately);
                record.setIdTemplate(idTem);
                //通知时间
                record.setNotifyTime(notifyTime);
                record.setNotifyContent(notifyContent);

                //体检者表
                Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
                try {
                    if(patient==null){
                        throw new ServiceException("体检号"+patientCode+"发送失败！,体检者已被删除!");
                    }else if(patient.getPhone()==null){
                        throw new ServiceException("体检号"+patientCode+"发送失败！,没有手机号码!");
                    }else{
                        SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                        String result_one = SDKTestSendTemplateSMS.sendMsg(smsConfig,patient.getPhone(), idTemplate, null,appId);
                        if(!"success".equals(result_one)){
                            throw new ServiceException("体检号"+patientCode+"<font color='red'>发送失败！</font>,"+result_one);
                        }else{
                            isSuccess = true;
//                            throw new ServiceException("体检号"+patientCode+"<font color='green'>发送成功！</font>");

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException("体检号"+patientCode+"发送失败！,"+e.getMessage());
                }
//                builder.append("</br>");
                if(isSuccess){
                    record.setNotifyResult(3);
                    //已经发送成功的不改变状态
                    if(patient!=null&&(patient.getIsNoticed()==null||patient.getIsNoticed().intValue()==0)){
                        //是否预约通知：0或null.否 1.是"
                        patient.setIsNoticed(1);
                        peispatientMapper.updateById(patient);
                    }
                }else{
                    record.setNotifyResult(0);
                }
                //添加或更新
                smsRecordService.saveOrUpdate(record);//第二次调用save时，Update
            }
            result=builder.toString();
        }else{//定时发送

            for(String patientCode:patientCodes){
                List<SmsRecord> records = smsRecordMapper.selectList(new QueryWrapper<SmsRecord>()
                                .orderByDesc("createDate").eq("patientcode",patientCode)
                                .eq("notify_type","0").eq("notify_result",2));//等待通知 可以修改
                SmsRecord record = null;
                if(records.size()==0){
                    record=new SmsRecord("0", patientCode);
                }else{
                    record=records.get(0);
                }
                record.setCreater(creater);
                record.setNotifyContent(notifyContent);
                record.setIsImmediately(isImmediately);
                record.setIdTemplate(idTem);
                record.setNotifyTime(notifyTime);
                record.setNotifyResult(2);
                smsRecordService.saveOrUpdate(record);
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 替换短信参数
     * @Title: getSmsContent
     * @param messageText
     * @param params
     * @return
     * String
     * @author xuhp
     * @since 2017-3-17 V 1.0
     */
    public static final String getSmsContent(String messageText,String[]params){
        if(params==null||messageText==null){
            return messageText;
        }
        for(int i=0,l=params.length;i<l;i++){
            messageText=messageText.replaceAll("\\{"+(i+1)+"\\}", params[i]);
        }
        return messageText;
    }
}

