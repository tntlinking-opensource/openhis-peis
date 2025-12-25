package com.center.medical.query.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检中心概况
 */
@Data
public class OverViewDto implements Serializable {
    private static final long serialVersionUID = -1342296512455875640L;

    @ApiModelProperty(value = "体检总人数")
    private Integer totalCount;

    @ApiModelProperty(value = "营业额")
    private Integer turnover;

    @ApiModelProperty(value = "折扣")
    private String discountRate;
}
