/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.response;

import cn.binarywang.wx.miniapp.json.WxMaGsonBuilder;
import com.center.medical.app.common.wx.bean.resp.LiveUserRespInfo;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 微信响应参数
 *
 * @author yami
 */
@Data
public class WxServerResponse<T> implements Serializable {


    private int errcode;

    private Integer total;

    private String msg;

    @SerializedName("list")
    private List<LiveUserRespInfo> list;

    private T roomInfo;

    private Long roomId;

    public boolean isSuccess() {
        return Objects.equals(ResponseCode.WX_SUCCESS, this.errcode);
    }

    public static WxServerResponse fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxServerResponse.class);
    }

}
