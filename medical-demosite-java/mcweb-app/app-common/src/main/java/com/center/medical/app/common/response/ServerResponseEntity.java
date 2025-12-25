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

import lombok.extern.slf4j.Slf4j;

/**
 * 响应实体
 *
 * @author yami
 */
@Slf4j
public class ServerResponseEntity {

    public static <T> ServerResponse<T> success(T data) {
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setObj(data);
        serverResponse.setCode(ResponseCode.SUCCESS);
        return serverResponse;
    }

    public static <T> ServerResponse<T> success() {
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setCode(ResponseCode.SUCCESS);
        return serverResponse;
    }

    public static <T> ServerResponse<T> success(Integer code, T data) {
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setCode(code);
        serverResponse.setObj(data);
        return serverResponse;
    }

    public static <T> ServerResponse<T> fail(String msg) {
        log.error(msg);
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setMsg(msg);
        serverResponse.setCode(ResponseCode.FAIL);
        return serverResponse;
    }

    public static <T> ServerResponse<T> fail(String msg, T data) {
        log.error(msg);
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setMsg(msg);
        serverResponse.setCode(ResponseCode.FAIL);
        serverResponse.setObj(data);
        return serverResponse;
    }

    public static <T> ServerResponse<T> fail(int code, String msg) {
        log.error(msg);
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setMsg(msg);
        serverResponse.setCode(code);
        return serverResponse;
    }

    public static <T> ServerResponse<T> fail(int code) {
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setCode(code);
        return serverResponse;
    }

    public static <T> ServerResponse<T> fail(int code, String msg, T data) {
        log.error(msg);
        ServerResponse<T> serverResponse = new ServerResponse<>();
        serverResponse.setMsg(msg);
        serverResponse.setCode(code);
        serverResponse.setObj(data);
        return serverResponse;
    }
}
