package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * QT卡办理收款方式表(CardPayway)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:31
 */
@Data
@TableName("CARD_PAYWAY")
@ApiModel(value = "CardPayway", description = "QT卡办理收款方式表实体类")
public class CardPayway extends Model<CardPayway> implements Serializable {
    private static final long serialVersionUID = -13948211857480560L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "已收费")
    private Integer isCharged;

    @ApiModelProperty(value = "${column.comment}")
    private String processId;
}
