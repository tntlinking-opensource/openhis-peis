/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.config;

import com.center.medical.enterprise.common.exception.CommonBindException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义错误处理器
 *
 * @author LGH
 */
@Slf4j
@Controller
@RestControllerAdvice
public class DefaultExceptionHandlerConfig {


    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> bindExceptionHandler(BindException e) {
        log.error("BindException:", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException:", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(CommonBindException.class)
    public ResponseEntity<String> unauthorizedExceptionHandler(CommonBindException e) {
        log.error("CommonBindException Message :{}", e.getMessage());
        return ResponseEntity.status(e.getHttpStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        log.error("Exception:", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
