package com.center.medical.app.common.wx.bean;

import lombok.Data;

/**
 * 微信小程序直播参数
 *
 * @author yami
 */
@Data
public class GoodsListReqParam extends WxInterfaceInfo {

    private Long offset;

    private Long limit;

    private Integer status;

    @Override
    public String getRequestUrl() {
        return "/getapproved?access_token=";
    }
}
