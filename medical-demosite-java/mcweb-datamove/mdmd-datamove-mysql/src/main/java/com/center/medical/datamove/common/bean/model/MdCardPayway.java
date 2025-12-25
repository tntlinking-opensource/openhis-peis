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
 * 卡办理收款方式表(MdCardPayway)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
@Data
@TableName("md_card_payway")
@ApiModel(value = "MdCardPayway", description = "卡办理收款方式表实体类")
public class MdCardPayway extends Model<MdCardPayway> implements Serializable {
    private static final long serialVersionUID = 109537266199698997L;

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

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

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
}
