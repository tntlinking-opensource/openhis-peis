package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者结算方式表(PeisReserPayway)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peis_reser_payway")
@ApiModel(value = "PeisReserPayway", description = "体检者结算方式表实体类")
public class PeisReserPayway extends Model<PeisReserPayway> implements Serializable {
    private static final long serialVersionUID = 559784351994297437L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检者结算表ID")
    private String idCharge;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "结算日期")
    private Date moneyamountpaiddate;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "已收费")
    private Integer isCharged;

    @TableField(exist = false)
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @TableField(exist = false)
    @ApiModelProperty(value = "收费员名称")
    private String userName;

}
