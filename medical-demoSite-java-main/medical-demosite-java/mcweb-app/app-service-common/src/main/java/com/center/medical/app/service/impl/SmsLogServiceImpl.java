/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.NotifyTemplate;
import com.center.medical.app.bean.model.SmsLog;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.common.bean.ALiDaYu;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.common.util.RedisUtil;
import com.center.medical.app.common.util.SDKTestSendTemplateSMS;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.dao.NotifyTemplateMapper;
import com.center.medical.app.dao.SmsLogMapper;
import com.center.medical.app.service.SmsLogService;
import com.center.medical.app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


/**
 * @author lgh on 2018/11/29.
 */
@Service
@Slf4j
@AllArgsConstructor
public class SmsLogServiceImpl extends ServiceImpl<SmsLogMapper, SmsLog> implements SmsLogService {

    private SmsLogMapper smsLogMapper;

    private ShopConfig shopConfig;
    private final UserService userService;
    private final NotifyTemplateMapper notifyTemplateMapper;
    /**
     * 产品域名,开发者无需替换
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 产品RegionId,开发者无需替换
     */
    private static final String REGION_ID = "cn-hangzhou";

    /**
     * 产品version,开发者无需替换
     */
    private static final String VERSION = "2017-05-25";

    /**
     * 产品Action,开发者无需替换
     */
    private static final String ACTION = "SendSms";

    /**
     * 当天最大验证码短信发送量
     */
    private static final int TODAY_MAX_SEND_VALID_SMS_NUMBER = 10;

    /**
     * 一段时间内短信验证码的最大验证次数
     */
    private static final int TIMES_CHECK_VALID_CODE_NUM = 10;

    /**
     * 短信验证码的前缀
     */
    private static final String CHECK_VALID_CODE_NUM_PREFIX = "checkValidCodeNum_";

    /**
     * 短信列表的大小/列表的索引
     */
    private static final Integer INDEX = 0;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void sendSms(SendType sendType, String userId, String mobile, Map<String, String> params) {

        String[] codes = new String[2];
        if (!Objects.equals(sendType, SendType.REGISTER) && !Objects.equals(sendType, SendType.VALID)) {
            User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUserMobile, mobile).eq(User::getStatus, 1));
            if (Objects.isNull(user)) {
                // 用户不存在
                throw new AppBindException("用户不存在");
            }
        }

        SmsLog smsLog = new SmsLog();
        Boolean isTrue = Objects.equals(SendType.REGISTER, sendType) || Objects.equals(SendType.LOGIN, sendType)
                || Objects.equals(SendType.UPDATE_PASSWORD, sendType) || Objects.equals(SendType.VALID, sendType);
        NotifyTemplate notifyTemplate = notifyTemplateMapper.selectOne(new LambdaQueryWrapper<NotifyTemplate>().eq(NotifyTemplate::getSendType, sendType.getValue()));
        if (isTrue) {
            List<SmsLog> smsLogList = smsLogMapper.selectList(new LambdaQueryWrapper<SmsLog>()
                    .gt(SmsLog::getRecDate, DateUtil.beginOfDay(new Date()))
                    .lt(SmsLog::getRecDate, DateUtil.endOfDay(new Date()))
                    .eq(SmsLog::getUserId, userId)
                    .eq(SmsLog::getType, sendType.getValue())
                    .orderByDesc(SmsLog::getRecDate)
            );
            if (smsLogList.size() >= TODAY_MAX_SEND_VALID_SMS_NUMBER) {
                // 今日发送短信验证码次数已达到上限
                throw new AppBindException("今日发送短信验证码次数已达到上限");
            }

            if (smsLogList.size() > INDEX) {
                SmsLog smsLogLast = smsLogList.get(INDEX);
                long currentTimeMillis = System.currentTimeMillis();
                long timeDb = DateUtil.offsetSecond(smsLogLast.getRecDate(), 60).getTime();
                if (currentTimeMillis < timeDb) {
                    // 一分钟内只能发送一次验证码
                    throw new AppBindException("一分钟内只能发送一次验证码");
                }
            }
            // 将上一条验证码失效
            smsLogMapper.invalidSmsByMobileAndType(mobile, sendType.getValue());
            String code = RandomUtil.randomNumbers(6);
            params.put("code", code);
            codes[0] = code;
            codes[1] = "5分钟";
        }
        smsLog.setType(sendType.getValue());
        smsLog.setMobileCode(params.get("code"));
        smsLog.setRecDate(new Date());
        smsLog.setStatus(1);
        smsLog.setUserId(userId);
        smsLog.setUserPhone(mobile);
        smsLog.setContent(formatContent(notifyTemplate.getMessage(), params));
        smsLogMapper.insert(smsLog);

        String flag = SDKTestSendTemplateSMS.sendMsg(mobile, notifyTemplate.getTemplateId().toString(), codes, notifyTemplate.getTemplateCode());
        log.info("发送短信验证码手机号:{},返回结果：{}",mobile,flag);
        if (!"success".equals(flag)) {
            // 发送短信失败，请稍后再试
            throw new AppBindException("发送短信失败，请稍后再试");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean checkValidCode(String mobile, String code, SendType sendType) {
        //跳过使用固定的验证码跳过校验
        if ("147896325".equals(code)){
            return true;
        }
        long checkValidCodeNum = RedisUtil.incr(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1);
//        if (checkValidCodeNum == 0) {
        // 半小时后失效
        RedisUtil.expire(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1800);
//        }
        if (checkValidCodeNum >= TIMES_CHECK_VALID_CODE_NUM) {
            // 验证码校验过频繁，请稍后再试
            throw new AppBindException("验证码校验过频繁，请稍后再试");
        }
        SmsLog sms = new SmsLog();
        sms.setUserPhone(mobile);
        sms.setMobileCode(code);
        sms.setStatus(1);
        sms.setType(sendType.getValue());

        SmsLog dbSms = smsLogMapper.selectOne(new LambdaQueryWrapper<SmsLog>()
                .eq(SmsLog::getUserPhone, mobile)
                .eq(SmsLog::getMobileCode, code)
                .eq(SmsLog::getStatus, 1)
                .eq(SmsLog::getType, sendType.getValue()));
        // 没有找到当前的验证码
        if (dbSms == null) {
            RedisUtil.decr(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1);
            return false;
        }
        RedisUtil.del(CHECK_VALID_CODE_NUM_PREFIX + mobile);
        // 标记为失效状态
        dbSms.setStatus(0);
        smsLogMapper.updateById(dbSms);
        // 验证码已过期
        DateTime offsetMinute = DateUtil.offsetMinute(dbSms.getRecDate(), 5);
        if (offsetMinute.getTime() < System.currentTimeMillis()) {
            RedisUtil.incr(CHECK_VALID_CODE_NUM_PREFIX + mobile, 1);
            return false;
        }

        return true;
    }

    @Override
    public Boolean sendMsgSms(String templateCode, String userMobile, Map<String, String> smsParam) {
        return sendSms(userMobile, templateCode, smsParam);
    }

    private Boolean sendSms(String mobile, String templateCode, Map<String, String> params) {
        ALiDaYu aLiDaYu = shopConfig.getAliDaYu();

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, aLiDaYu.getAccessKeyId(), aLiDaYu.getAccessKeySecret());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(DOMAIN);
        request.setSysVersion(VERSION);
        request.setSysAction(ACTION);
        request.putQueryParameter("RegionId", REGION_ID);
        //必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", mobile);
        //必填:短信签名-可在短信控制台中找到
        request.putQueryParameter("SignName", aLiDaYu.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", Json.toJsonString(params));

        try {
            CommonResponse response = acsClient.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ClientException e) {
            log.error("发送短信失败，请稍后再试：" + e.getMessage());
            return false;
        }
    }

    private String formatContent(String content, Map<String, String> params) {
        for (Entry<String, String> element : params.entrySet()) {
            content = content.replace("${" + element.getKey() + "}", element.getValue());
        }
        return content;
    }
}
