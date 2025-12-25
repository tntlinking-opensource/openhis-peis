package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-31 10:33
 * @description: 合作第三方配置设置
 */
@Data
public class AuthItemSetting implements Serializable {
    private static final long serialVersionUID = 1476335495351769929L;

    @ApiModelProperty(value = "合作方接口地址，如：http://www.xxxx.com")
    private String host;
}
