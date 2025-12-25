package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CreateorderVo implements Serializable {

    private static final long serialVersionUID = -5620924216593279498L;

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "备单日期")
    private Date bdrq;

    @ApiModelProperty(value = "客户单位团体号")
    private String intId;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

}
