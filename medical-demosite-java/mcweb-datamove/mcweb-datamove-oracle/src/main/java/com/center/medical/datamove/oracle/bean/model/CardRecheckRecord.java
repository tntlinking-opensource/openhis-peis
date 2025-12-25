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
 * (CardRecheckRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:32
 */
@Data
@TableName("CARD_RECHECK_RECORD")
@ApiModel(value = "CardRecheckRecord", description = "$tableInfo.comment实体类")
public class CardRecheckRecord extends Model<CardRecheckRecord> implements Serializable {
    private static final long serialVersionUID = -68139788494102094L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Double startMoney;

    @ApiModelProperty(value = "${column.comment}")
    private Double endMoney;

    @ApiModelProperty(value = "${column.comment}")
    private Double money;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private String cardNo;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Date chargeTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer chargeType;
}
