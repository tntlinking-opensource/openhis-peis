package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡类型(CardType)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_card_type")
@ApiModel(value = "CardType", description = "卡类型实体类")
public class CardType extends Model<CardType> implements Serializable {
    private static final long serialVersionUID = -34569159917834332L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "卡金额")
    private Double cardMoney;

    @ApiModelProperty(value = "是否可充值：0或null.否 1.是")
    private Integer isRecharge;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "积分（仅限会员卡）")
    private Integer jf;

    @ApiModelProperty(value = "type")
    private Integer type;

    @ApiModelProperty(value = "卡分类标识：1.体检卡 2.会员卡 3.家庭卡")
    private Integer flag;

    @ApiModelProperty(value = "分中心id")
    private String cid;
}
