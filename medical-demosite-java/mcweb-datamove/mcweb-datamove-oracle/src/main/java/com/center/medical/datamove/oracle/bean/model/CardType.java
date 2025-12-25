package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * CW卡类型(CardType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:34
 */
@Data
@TableName("CARD_TYPE")
@ApiModel(value = "CardType", description = "CW卡类型实体类")
public class CardType extends Model<CardType> implements Serializable {
    private static final long serialVersionUID = 194403415460520934L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "卡金额")
    private Double cardMoney;

    @ApiModelProperty(value = "是否可充值")
    private Integer isRecharge;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "积分（仅限会员卡）")
    private Double jf;

    @ApiModelProperty(value = "${column.comment}")
    private Integer type;

    @ApiModelProperty(value = "${column.comment}")
    private Integer flag;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;
}
