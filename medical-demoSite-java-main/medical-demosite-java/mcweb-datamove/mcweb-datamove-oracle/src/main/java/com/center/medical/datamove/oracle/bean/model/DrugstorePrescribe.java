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
 * (DrugstorePrescribe)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:30
 */
@Data
@TableName("DRUGSTORE_PRESCRIBE")
@ApiModel(value = "DrugstorePrescribe", description = "$tableInfo.comment实体类")
public class DrugstorePrescribe extends Model<DrugstorePrescribe> implements Serializable {
    private static final long serialVersionUID = 871742671205504914L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "药品ID")
    private String drugId;

    @ApiModelProperty(value = "医生用户名")
    private String username;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "开药原因")
    private String reason;

    @ApiModelProperty(value = "开药时间")
    private Date prescribeTime;

    @ApiModelProperty(value = "开药数量")
    private Double prescribeNum;

    @ApiModelProperty(value = "成交价")
    private Double transactionPrice;

    @ApiModelProperty(value = "是否已开药（0否1是）")
    private Integer isFinished;

    @ApiModelProperty(value = "备注")
    private String prescribeNote;
}
