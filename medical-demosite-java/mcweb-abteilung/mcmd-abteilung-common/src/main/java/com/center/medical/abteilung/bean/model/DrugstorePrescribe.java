package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开药记录(DrugstorePrescribe)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_drugstore_prescribe")
@ApiModel(value = "DrugstorePrescribe", description = "开药记录实体类")
public class DrugstorePrescribe extends Model<DrugstorePrescribe> implements Serializable {
    private static final long serialVersionUID = 860396652686074636L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
