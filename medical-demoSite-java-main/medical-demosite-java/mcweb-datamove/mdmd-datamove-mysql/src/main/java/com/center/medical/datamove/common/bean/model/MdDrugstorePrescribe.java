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
 * 开药记录(MdDrugstorePrescribe)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
@Data
@TableName("md_drugstore_prescribe")
@ApiModel(value = "MdDrugstorePrescribe", description = "开药记录实体类")
public class MdDrugstorePrescribe extends Model<MdDrugstorePrescribe> implements Serializable {
    private static final long serialVersionUID = -39438102147175875L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
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
    private Integer prescribeNum;

    @ApiModelProperty(value = "成交价")
    private Integer transactionPrice;

    @ApiModelProperty(value = "是否已开药：0.否 1.已成交 2.未成交 3.退药")
    private Integer isFinished;

    @ApiModelProperty(value = "备注")
    private String prescribeNote;
}
