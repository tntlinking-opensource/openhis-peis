package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付分页参数
 */
@Data
public class SuixingPayConfigParam implements Serializable {
    private static final long serialVersionUID = -7344360048889323164L;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

}
