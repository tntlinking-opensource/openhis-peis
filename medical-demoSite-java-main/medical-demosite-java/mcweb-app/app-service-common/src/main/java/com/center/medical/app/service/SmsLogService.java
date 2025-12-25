/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.SmsLog;

import java.util.Map;

/**
 * @author lgh on 2018/11/29.
 */
public interface SmsLogService extends IService<SmsLog> {

    /**
     * 发送短信
     *
     * @param smsType 短信类型
     * @param userId  用户id
     * @param mobile  电话号码
     * @param params  发送内容
     */
    public void sendSms(SendType smsType, String userId, String mobile, Map<String, String> params);

    /**
     * 校验短信验证码
     *
     * @param mobile   电话号码
     * @param code     验证码
     * @param sendType 发送类型
     * @return
     */
    public boolean checkValidCode(String mobile, String code, SendType sendType);

    /**
     * 发送短信
     *
     * @param templateCode 模板code
     * @param userMobile   用户电话号
     * @param smsParam     短信内容
     * @return 是否
     */
    Boolean sendMsgSms(String templateCode, String userMobile, Map<String, String> smsParam);

}
