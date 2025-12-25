package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 加项费用、加项人数
 */
@Data
public class JiaXiangDto implements Serializable {
    private static final long serialVersionUID = -8878193100894064240L;

    @ApiModelProperty(value = "个数")
    private String count;

    @ApiModelProperty(value = "实付价格")
    private String factprice;
}
