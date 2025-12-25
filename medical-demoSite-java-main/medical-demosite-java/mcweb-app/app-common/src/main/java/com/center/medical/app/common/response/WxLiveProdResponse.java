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

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 微信直播商品响应参数
 *
 * @author yami
 */
@Data
public class WxLiveProdResponse<T> implements Serializable {


    private int errcode;

    private Integer total;

    private String msg;

    private T goods;

    private Long roomId;

    public boolean isSuccess() {
        return Objects.equals(ResponseCode.WX_SUCCESS, this.errcode);
    }


}
