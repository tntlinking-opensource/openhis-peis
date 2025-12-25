package com.center.medical.app.common.wx.bean;

import lombok.Data;

import java.util.List;

/**
 * 微信小程序直播参数
 *
 * @author yami
 */
@Data
public class GoodsReqInfo extends WxInterfaceInfo {

    /**
     * 商品ID
     */
    private Long goodsId;

    private List<Long> goodsIds;
    /**
     * 审核单ID
     */
    private Long auditId;

    private String requestUrl;

    @Override
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
