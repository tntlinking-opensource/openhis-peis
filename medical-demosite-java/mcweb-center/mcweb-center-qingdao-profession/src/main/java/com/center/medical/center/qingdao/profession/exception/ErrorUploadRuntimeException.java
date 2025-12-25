package com.center.medical.center.qingdao.profession.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorUploadRuntimeException extends RuntimeException{

    public ErrorUploadRuntimeException() {
    }

    public ErrorUploadRuntimeException(String message) {
        super(message);
    }

    public ErrorUploadRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorUploadRuntimeException(Throwable cause) {
        super(cause);
    }

    public ErrorUploadRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}