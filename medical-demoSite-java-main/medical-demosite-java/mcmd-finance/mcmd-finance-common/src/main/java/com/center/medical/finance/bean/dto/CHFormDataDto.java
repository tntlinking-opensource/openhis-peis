package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 体检卡办理 体检卡数据 保存或修改
 */
@Data
public class CHFormDataDto implements Serializable {
    private static final long serialVersionUID = -9173092441110138401L;

    @ApiModelProperty(value = "id集合")
    private List<String> ids;

    @ApiModelProperty(value = "售卡人ID")
    private String sellId;

    @ApiModelProperty(value = "售卡人姓名")
    private String sellName;

    @ApiModelProperty(value = "出售时间")
    private Date sellTime;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "套餐价格")
    private Integer tcPrice;

    @ApiModelProperty(value = "卡分类名称")
    private String typeName;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "有效期")
    private Date validity;

    @ApiModelProperty(value = "卡说明")
    private String cardState;

    @ApiModelProperty(value = "套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "售价")
    private Double sellPrice;

    @ApiModelProperty(value = "折扣")
    private Double zk;

    @ApiModelProperty(value = "总价格")
    private Double totalPrice;

    @ApiModelProperty(value = "总折扣价格")
    private Double totalSellPrice;

    @ApiModelProperty(value = "购卡人名字（不一定在系统中）")
    private String purchaser;

    @ApiModelProperty(value = "购卡人手机号")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String memo;


}
