/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.response;

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
