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
 * 响应类
 *
 * @author yami
 */
@Data
public class ServerResponse<T> implements Serializable {


    private int code;

    private String msg;

    private T obj;

    public boolean isSuccess() {
        return Objects.equals(ResponseCode.SUCCESS, this.code);
    }


}
