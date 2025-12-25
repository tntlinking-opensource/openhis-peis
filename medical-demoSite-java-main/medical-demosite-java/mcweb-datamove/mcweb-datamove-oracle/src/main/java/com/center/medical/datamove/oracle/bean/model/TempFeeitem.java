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
 * (TempFeeitem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:15
 */
@Data
@TableName("TEMP_FEEITEM")
@ApiModel(value = "TempFeeitem", description = "$tableInfo.comment实体类")
public class TempFeeitem extends Model<TempFeeitem> implements Serializable {
    private static final long serialVersionUID = -24927486575509857L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String itemId;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorUsername;

    @ApiModelProperty(value = "${column.comment}")
    private Double price;

    @ApiModelProperty(value = "${column.comment}")
    private String remarks;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String feeitemId;
}
