/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单相关的配置信息
 *
 * @author 路飞
 */
@Data
public class OrderConfig {

    @ApiModelProperty(value = "同步订单的天数，-1不限制")
    private Integer tbddDays;

}
