package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通联支付设置，详见https://aipboss.allinpay.com/know/devhelp/main.php?pid=15#mid=88
 */
@Data
public class TongLianConfig implements Serializable {
    private static final long serialVersionUID = 8863837236626313748L;

    @ApiModelProperty(value = "集团/代理商商户号")
    private String SYB_ORGID;

    @ApiModelProperty(value = "商户号")
    private String SYB_CUSID;

    @ApiModelProperty(value = "应用ID")
    private String SYB_APPID;

    @ApiModelProperty(value = "md5")
    private String SYB_MD5_APPKEY;

    @ApiModelProperty(value = "生产环境")
    private String SYB_APIURL;

    @ApiModelProperty(value = "加密方式")
    private String SIGN_TYPE;

    @ApiModelProperty(value = "设备号,要用的话得重新报备")
    private String TERMINFO;

    @ApiModelProperty(value = "rsa私钥")
    private String SYB_RSACUSPRIKEY;

    @ApiModelProperty(value = "rsa公钥")
    private String SYB_RSATLPUBKEY;

    @ApiModelProperty(value = "sm2私钥")
    private String SYB_SM2PPRIVATEKEY;

    @ApiModelProperty(value = "sm2公钥")
    private String SYB_SM2TLPUBKEY;

}
