package com.center.medical.app.common.bean;

import lombok.Data;

/**
 * 系统支付开关配置
 *
 * @author cl
 */
@Data
public class SysPayConfig {

    /**
     * 是否开启支付宝支付
     */
    private Boolean aliPaySwitch;

    /**
     * 是否开启支付宝支付
     */
    private Boolean wxPaySwitch;

    /**
     * 是否开启支付宝支付
     */
    private Boolean balancePaySwitch;

    /**
     * 是否开启支付宝支付
     */
    private Boolean payPalSwitch;

}
