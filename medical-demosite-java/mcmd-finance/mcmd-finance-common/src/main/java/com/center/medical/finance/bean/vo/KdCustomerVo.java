package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取金蝶客户
 */
@Data
public class KdCustomerVo implements Serializable {
    private static final long serialVersionUID = 958395053195940637L;


    @ApiModelProperty(value = "账户号")
    private String kingdeeAccountNo;

    @ApiModelProperty(value = "账户名称")
    private String kingdeeAccountName;

    @ApiModelProperty(value = "centerorgname")
    private String centerOrgName;

    @ApiModelProperty(value = "使用状态id 0禁用 1开启")
    private String kingdeeUseStatus;

}
