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
 * (ReservationReturnVisit)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:20
 */
@Data
@TableName("RESERVATION_RETURN_VISIT")
@ApiModel(value = "ReservationReturnVisit", description = "$tableInfo.comment实体类")
public class ReservationReturnVisit extends Model<ReservationReturnVisit> implements Serializable {
    private static final long serialVersionUID = 154832691993029149L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientId;

    @ApiModelProperty(value = "${column.comment}")
    private String visitId;

    @ApiModelProperty(value = "${column.comment}")
    private Date visitTime;

    @ApiModelProperty(value = "${column.comment}")
    private String visitNote;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sflj;

    @ApiModelProperty(value = "${column.comment}")
    private Date ljsj;
}
