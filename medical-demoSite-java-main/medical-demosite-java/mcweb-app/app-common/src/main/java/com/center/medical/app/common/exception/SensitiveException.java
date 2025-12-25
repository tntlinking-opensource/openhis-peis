package com.center.medical.app.common.exception;

import cn.hutool.http.HttpStatus;

/**
 * @author Citrus
 * @date 2021/8/17 14:51
 */
public class SensitiveException extends RuntimeException {

    private static final long serialVersionUID = -4137688758944857209L;
    /**
     * http状态码
     */
    private Integer httpStatusCode;

    private Object object;

    public SensitiveException(String msg) {
        super(msg);//(I18nMessage.getMessage(msg));
        //敏感词的异常
        this.httpStatusCode = HttpStatus.HTTP_BAD_REQUEST;
    }
}
