package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (FamilyCardCharge)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:42
 */
@Data
@TableName("FAMILY_CARD_CHARGE")
@ApiModel(value = "FamilyCardCharge", description = "$tableInfo.comment实体类")
public class FamilyCardCharge extends Model<FamilyCardCharge> implements Serializable {
    private static final long serialVersionUID = 626432114783596869L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "操作人用户名")
    private String chargerUsername;

    @ApiModelProperty(value = "操作时间")
    private Date chargeTime;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "操作类型")
    private Integer type;

    @ApiModelProperty(value = "积分")
    private Double jf;

    @ApiModelProperty(value = "充值前积分")
    private Double startJf;

    @ApiModelProperty(value = "充值后积分")
    private Double endJf;

    @ApiModelProperty(value = "充值前金额")
    private Double startMoney;

    @ApiModelProperty(value = "充值后金额")
    private Double endMoney;

    @ApiModelProperty(value = "${column.comment}")
    private String consumetype;
}
