/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.SmsLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author yami
 */
public interface SmsLogMapper extends BaseMapper<SmsLog> {

    /**
     * 失效短信记录
     *
     * @param mobile 电话号码
     * @param type   类型
     */
    void invalidSmsByMobileAndType(@Param("mobile") String mobile, @Param("type") Integer type);
}
