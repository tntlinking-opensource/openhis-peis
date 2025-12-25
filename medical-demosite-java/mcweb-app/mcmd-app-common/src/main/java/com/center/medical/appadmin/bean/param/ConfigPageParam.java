package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置分页参数
 */
@Data
public class ConfigPageParam implements Serializable {
    private static final long serialVersionUID = -4513630007732067448L;

    @ApiModelProperty(value = "key")
    private String paramKey;

}
