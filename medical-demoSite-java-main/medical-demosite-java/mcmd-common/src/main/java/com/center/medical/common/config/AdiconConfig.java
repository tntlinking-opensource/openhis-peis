package com.center.medical.common.config;

import lombok.Data;

/**
 * 艾迪康参数配置
 * @author xhp
 * @since 2023-11-20 11:11
 */
@Data
public class AdiconConfig {
    //登录id
    private String loginid;
    //密码
    private String password;
    //接口地址
    private String serviceUrl;
    //检验师用户名 优先取这里，其次从艾迪康获取
    private String inspectName;
    //审核医师用户名 优先取这里，其次从艾迪康获取
    private String auditName;
}
