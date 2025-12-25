package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取账户名称
 */
@Data
public class AccountNameDto implements Serializable {
    private static final long serialVersionUID = 5451549630904793725L;


    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "账户号")
    private String accountNo;

}
