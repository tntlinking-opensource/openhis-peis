package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取汇款单位名单
 */
@Data
public class RemitterVo implements Serializable {
    private static final long serialVersionUID = -768219102527713466L;

    @ApiModelProperty(value = "对方户名")
    private String remitter;
}
