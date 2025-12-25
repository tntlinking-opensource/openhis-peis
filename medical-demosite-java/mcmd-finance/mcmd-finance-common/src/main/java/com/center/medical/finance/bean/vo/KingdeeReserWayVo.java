package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取银行结算方式列表
 */
@Data
public class KingdeeReserWayVo implements Serializable {
    private static final long serialVersionUID = -3789997626253790759L;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "账户号")
    private String accountNo;

}
