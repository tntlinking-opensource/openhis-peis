package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-05-17 8:42
 * @description: 撞单的订单信息
 */
@Data
public class ConflictOrderVo implements Serializable {
    private static final long serialVersionUID = 1875253581877752661L;

    @ApiModelProperty(value = "订单id", position = 1, required = true)
    private String id;

    @ApiModelProperty(value = "订单号", position = 2, required = true)
    private String ddh;

    @ApiModelProperty(value = "订单名称", position = 2, required = true)
    private String ddmc;

    @ApiModelProperty(value = "销售经理", position = 3, required = true)
    private String xsjl;

    @ApiModelProperty(value = "分中心", position = 4, required = true)
    private String fzx;

    @ApiModelProperty(value = "客户单位团体号", position = 5, required = true)
    private String intId;

    @ApiModelProperty(value = "客户单位名称", position = 6, required = true)
    private String khdwmc;

    @ApiModelProperty(value = "客户单位联系人", position = 7, required = true)
    private String khdwlxr;

    @ApiModelProperty(value = "客户电话", position = 8, required = true)
    private String khdh;

    @ApiModelProperty(value = "法人单位名称", position = 9, required = true)
    private String frdwmc;

    @ApiModelProperty(value = "营业执照上的企业名称用工客户单位名称", position = 11, required = true)
    private String licenseName;

    @ApiModelProperty(value = "社会信用代码", position = 12, required = true)
    private String socialCreditCode;

    @ApiModelProperty(value = "省级代码", position = 13, required = true)
    private String province;

    @ApiModelProperty(value = "市级代码", position = 14, required = true)
    private String city;

    @ApiModelProperty(value = "区级代码", position = 15, required = true)
    private String district;

    @ApiModelProperty(value = "街道代码", position = 16, required = true)
    private String street;

    @ApiModelProperty(value = "客户单位注册地址", position = 17, required = true)
    private String khdwzcdz;

    @ApiModelProperty(value = "备注", position = 18, required = true)
    private String bz;
}
