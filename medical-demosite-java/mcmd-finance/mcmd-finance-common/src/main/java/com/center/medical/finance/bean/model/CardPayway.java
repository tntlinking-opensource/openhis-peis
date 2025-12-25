package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡办理收款方式表(CardPayway)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_card_payway")
@ApiModel(value = "CardPayway", description = "卡办理收款方式表实体类")
public class CardPayway extends Model<CardPayway> implements Serializable {
    private static final long serialVersionUID = 814509994300934862L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "卡ID")
    private String idCharge;

    @ApiModelProperty(value = "收费金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "收费日期")
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

    @ApiModelProperty(value = "已收费：1已收")
    private Integer isCharged;

    @ApiModelProperty(value = "卡办理id（对应card processId）")
    private String processId;

    @TableField(exist = false)
    @ApiModelProperty(value = "收费员姓名")
    private String feechargerName;

    @TableField(exist = false)
    @ApiModelProperty(value = "付款方式名称")
    private String paywayName;

}
