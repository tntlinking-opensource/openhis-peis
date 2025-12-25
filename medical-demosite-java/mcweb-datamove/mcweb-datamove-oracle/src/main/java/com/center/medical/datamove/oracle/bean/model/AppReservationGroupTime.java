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
 * 小程序团检预约各时间段人数(AppReservationGroupTime)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:56
 */
@Data
@TableName("APP_RESERVATION_GROUP_TIME")
@ApiModel(value = "AppReservationGroupTime", description = "小程序团检预约各时间段人数实体类")
public class AppReservationGroupTime extends Model<AppReservationGroupTime> implements Serializable {
    private static final long serialVersionUID = -37608633510329160L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String amount;

    @ApiModelProperty(value = "${column.comment}")
    private String groupId;

    @ApiModelProperty(value = "${column.comment}")
    private Date startTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date endTime;

    @ApiModelProperty(value = "${column.comment}")
    private String used;

    @ApiModelProperty(value = "${column.comment}")
    private String timeRange;
}
