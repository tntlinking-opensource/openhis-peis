package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售员回款 分页 参数
 */
@Data
public class FIPageParam implements Serializable {
    private static final long serialVersionUID = 2144053225847384288L;


    @ApiModelProperty(value = "年份")
    private String listYear;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;


}
