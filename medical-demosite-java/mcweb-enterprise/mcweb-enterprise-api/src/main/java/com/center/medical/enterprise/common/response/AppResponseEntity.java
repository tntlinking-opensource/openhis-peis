package com.center.medical.enterprise.common.response;

import lombok.extern.slf4j.Slf4j;

/**
 * 响应实体
 *
 * @author 路飞
 */
@Slf4j
public class AppResponseEntity {

    public static <T> AppResponse<T> success(T data) {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setData(data);
        appResponse.setCode(AppResponse.SUCCESS);
        return appResponse;
    }

    public static <T> AppResponse<T> success() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setCode(AppResponse.SUCCESS);
        return appResponse;
    }

    public static <T> AppResponse<T> success(Integer code, T data) {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setCode(code);
        appResponse.setData(data);
        return appResponse;
    }

    public static <T> AppResponse<T> fail(String msg) {
        log.error(msg);
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setMsg(msg);
        appResponse.setCode(AppResponse.BAD_REQUEST);
        return appResponse;
    }

    public static <T> AppResponse<T> fail(String msg, T data) {
        log.error(msg);
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setMsg(msg);
        appResponse.setCode(AppResponse.BAD_REQUEST);
        appResponse.setData(data);
        return appResponse;
    }

    public static <T> AppResponse<T> fail(int code, String msg) {
        log.error(msg);
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setMsg(msg);
        appResponse.setCode(code);
        return appResponse;
    }

    public static <T> AppResponse<T> fail(int code) {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setCode(code);
        return appResponse;
    }

    public static <T> AppResponse<T> fail(int code, String msg, T data) {
        log.error(msg);
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setMsg(msg);
        appResponse.setCode(code);
        appResponse.setData(data);
        return appResponse;
    }
}
