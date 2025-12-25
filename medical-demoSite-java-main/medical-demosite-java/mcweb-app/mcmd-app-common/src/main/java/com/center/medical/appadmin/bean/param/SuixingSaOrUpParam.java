package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 随行支付添加或保存参数
 */
@Data
public class SuixingSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 6336951720356035501L;

    @ApiModelProperty(value = "记录ID")
    private String id;

    @NotBlank(message = "请填写分中心id!")
    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @NotBlank(message = "请填写机构编号!")
    @ApiModelProperty(value = "机构编号")
    private String orgId;

    @NotBlank(message = "请填写商户编号!")
    @ApiModelProperty(value = "商户编号")
    private String mno;

    @NotBlank(message = "请填写私钥!")
    @ApiModelProperty(value = "私钥")
    private String privateKey;
}
