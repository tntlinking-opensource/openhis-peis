package com.center.medical.app.common.wx.bean.resp;

import lombok.Data;

/**
 * 微信小程序直播参数
 *
 * @author yami
 */
@Data
public class GoodsListRespParam {

    private Long errcode;

    private String errmsg;

    private GoodsListInfosRespParam goods;
    /**
     * 商品个数
     */
    private Integer total;
}
