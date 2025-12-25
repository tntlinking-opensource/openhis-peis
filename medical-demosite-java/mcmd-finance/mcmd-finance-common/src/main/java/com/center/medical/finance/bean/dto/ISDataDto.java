package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 存放指标情况查询数据
 */
@Data
public class ISDataDto implements Serializable {
    private static final long serialVersionUID = 6910332114764035972L;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "数据")
    private Double value;

}
