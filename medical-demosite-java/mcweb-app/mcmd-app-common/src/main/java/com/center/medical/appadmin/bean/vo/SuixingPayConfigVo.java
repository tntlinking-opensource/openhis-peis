package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付 分页参数
 */
@Data
public class SuixingPayConfigVo implements Serializable {
    private static final long serialVersionUID = 1828756993438471783L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心Id")
    private String branchId;

    @ApiModelProperty(value = "机构编号")
    private String orgId;

    @ApiModelProperty(value = "商户编号")
    private String mno;

    @ApiModelProperty(value = "创建时间")
    private String createdate;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;
}
