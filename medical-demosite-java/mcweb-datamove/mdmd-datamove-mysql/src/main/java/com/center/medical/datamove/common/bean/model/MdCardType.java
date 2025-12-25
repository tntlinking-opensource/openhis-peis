package com.center.medical.datamove.common.bean.model;


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
 * 卡类型(MdCardType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
@Data
@TableName("md_card_type")
@ApiModel(value = "MdCardType", description = "卡类型实体类")
public class MdCardType extends Model<MdCardType> implements Serializable {
    private static final long serialVersionUID = 683586538043077652L;

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
    private Date createdate;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "修改日期")
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
