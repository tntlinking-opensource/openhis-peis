package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团检订单折扣 分页 返回数据
 */
@Data
public class OCPageVo implements Serializable {
    private static final long serialVersionUID = 7082798629090201867L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "订单折扣")
    private Double ddzk;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位电话")
    private String khdwdh;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "创建订单日期")
    private Date cjddrq;

    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "分中心")
    private String fzx;


}
