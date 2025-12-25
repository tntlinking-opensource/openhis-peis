package com.center.medical.common.config;

import lombok.Data;

/**
 * 艾迪康参数配置
 * @author xhp
 * @since 2023-11-20 11:11
 */
@Data
public class PingAnConfig {
    //健康互联网分配的唯一 key，用作校验用户是否合法
    private String pajkKey;
    //密码
    private String pajkPwd;
    //时间戳
    private String timestamp;
    //到检核销的提交网址
    private String confirmUrl;
    //上传用户pdf的上传地址
    private String pdfUrl;

}
