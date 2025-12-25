package com.center.medical.outside.bean.param;

import lombok.Data;

/**
 * @author: 路飞船长
 * @date: 2025/6/16 10:25
 * @description: 第三方登录参数
 */
@Data
public class OutsideLoginParam {

    private String userName;
    private String password;
    private String sourceId;
}
