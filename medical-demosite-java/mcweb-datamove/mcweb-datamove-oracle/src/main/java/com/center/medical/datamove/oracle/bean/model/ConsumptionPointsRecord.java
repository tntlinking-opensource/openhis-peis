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
 * (ConsumptionPointsRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:49
 */
@Data
@TableName("CONSUMPTION_POINTS_RECORD")
@ApiModel(value = "ConsumptionPointsRecord", description = "$tableInfo.comment实体类")
public class ConsumptionPointsRecord extends Model<ConsumptionPointsRecord> implements Serializable {
    private static final long serialVersionUID = 301969468859136837L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private String idCardNo;

    @ApiModelProperty(value = "${column.comment}")
    private String total;

    @ApiModelProperty(value = "${column.comment}")
    private String description;

    @ApiModelProperty(value = "${column.comment}")
    private String paymentMethod;

    @ApiModelProperty(value = "${column.comment}")
    private String type;

    @ApiModelProperty(value = "${column.comment}")
    private String points;

    @ApiModelProperty(value = "${column.comment}")
    private String isSuccess;

    @ApiModelProperty(value = "${column.comment}")
    private String createBy;

    @ApiModelProperty(value = "${column.comment}")
    private Date createDate;
}
