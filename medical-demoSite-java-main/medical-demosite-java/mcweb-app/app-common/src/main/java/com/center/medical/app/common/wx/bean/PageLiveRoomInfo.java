package com.center.medical.app.common.wx.bean;

import lombok.Data;

/**
 * 微信直播间接口所需参数
 *
 * @author LHD
 */
@Data
public class PageLiveRoomInfo extends WxInterfaceInfo {

    private Long roomId;

    @Override
    public String getRequestUrl() {
        return "/wxa/business/getliveinfo?access_token=";
    }

}
