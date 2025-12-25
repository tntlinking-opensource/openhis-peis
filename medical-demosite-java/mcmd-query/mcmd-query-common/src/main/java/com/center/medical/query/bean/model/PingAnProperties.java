package com.center.medical.query.bean.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * 平安参数设置
 */
@Data
public class PingAnProperties implements Serializable {
    private static final long serialVersionUID = 4968736406564931593L;

    @Value("${pay.proxyUrl}")
    private String proxyUrl;


}
