package com.center.medical.center.qingdao.profession.exception;

import com.center.medical.center.qingdao.profession.entity.dto.Info;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorRuntimeException extends RuntimeException {
    private String code;
    private String msg;
    private List<Info> infos;

    public ErrorRuntimeException(List<Info> infos) {
        this.infos = infos;
    }

    public ErrorRuntimeException(String message) {
        super(message);
    }

    public ErrorRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorRuntimeException(Throwable cause) {
        super(cause);
    }

    public ErrorRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorRuntimeException(String code, String msg, List<Info> infoX) {

        this.code = code;
        this.msg = msg;
        infos = infoX;
    }
}
