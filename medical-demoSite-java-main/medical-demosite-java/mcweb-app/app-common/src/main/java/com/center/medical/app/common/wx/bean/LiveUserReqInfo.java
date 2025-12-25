package com.center.medical.app.common.wx.bean;

import lombok.Data;

/**
 * 直播用户请求参数
 *
 * @author yami
 */
@Data
public class LiveUserReqInfo extends WxInterfaceInfo {

    /**
     * 主播微信号
     */
    private String username;
    /**
     * 角色列表
     */
    private Integer role;
}
