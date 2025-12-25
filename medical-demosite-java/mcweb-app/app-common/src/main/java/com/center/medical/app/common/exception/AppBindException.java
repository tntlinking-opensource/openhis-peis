/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.exception;

import com.center.medical.app.common.enums.AppHttpStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常
 *
 * @author yami
 */
@Getter
public class AppBindException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4137688758944857209L;

    /**
     * http状态码
     */
    private Integer httpStatusCode;

    private Object object;

    /**
     * @param httpStatus http状态码
     */
    public AppBindException(AppHttpStatus httpStatus) {
        super(httpStatus.getMsg());
        this.httpStatusCode = httpStatus.value();
    }

    /**
     * @param httpStatus http状态码
     */
    public AppBindException(AppHttpStatus httpStatus, String msg) {
        super(msg);//(I18nMessage.getMessage(msg));
        this.httpStatusCode = httpStatus.value();
    }


    public AppBindException(String msg) {
//		super(msg);
        super(msg); //(I18nMessage.getMessage(msg));
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
    }

    public AppBindException(String msg, Object object) {
        super(msg);//(I18nMessage.getMessage(msg));
        this.httpStatusCode = HttpStatus.BAD_REQUEST.value();
        this.object = object;
    }

}
