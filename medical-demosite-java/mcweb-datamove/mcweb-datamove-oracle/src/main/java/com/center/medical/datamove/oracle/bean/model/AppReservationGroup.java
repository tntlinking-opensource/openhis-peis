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
 * 小程序团检预约人数(AppReservationGroup)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:55
 */
@Data
@TableName("APP_RESERVATION_GROUP")
@ApiModel(value = "AppReservationGroup", description = "小程序团检预约人数实体类")
public class AppReservationGroup extends Model<AppReservationGroup> implements Serializable {
    private static final long serialVersionUID = 367456134232209196L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date reservationDate;

    @ApiModelProperty(value = "${column.comment}")
    private String amount;

    @ApiModelProperty(value = "${column.comment}")
    private String creator;

    @ApiModelProperty(value = "${column.comment}")
    private String modifier;

    @ApiModelProperty(value = "${column.comment}")
    private String orderId;

    @ApiModelProperty(value = "${column.comment}")
    private String used;

    @ApiModelProperty(value = "${column.comment}")
    private String am;

    @ApiModelProperty(value = "${column.comment}")
    private String pm;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;
}
