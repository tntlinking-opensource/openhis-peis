package com.center.medical.app.common.wx.bean;

import lombok.Data;

import java.util.List;

/**
 * 微信直播间商品详情参数
 *
 * @author yami
 */
@Data
public class WxRoomProdInfo extends WxInterfaceInfo {

    private List<Long> ids;

    private Long roomId;

    @Override
    public String getRequestUrl() {
        return "/wxaapi/broadcast/room/addgoods?access_token=";
    }

}
