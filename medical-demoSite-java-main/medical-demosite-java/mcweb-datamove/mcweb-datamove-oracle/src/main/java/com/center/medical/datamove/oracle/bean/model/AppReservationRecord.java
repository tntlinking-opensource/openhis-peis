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
 * 小程序预约记录（团检+个检）(AppReservationRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:59
 */
@Data
@TableName("APP_RESERVATION_RECORD")
@ApiModel(value = "AppReservationRecord", description = "小程序预约记录（团检+个检）实体类")
public class AppReservationRecord extends Model<AppReservationRecord> implements Serializable {
    private static final long serialVersionUID = -23498788293708340L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String timeRange;

    @ApiModelProperty(value = "${column.comment}")
    private Date startTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date endTime;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isGroup;

    @ApiModelProperty(value = "${column.comment}")
    private Date reservationDate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer timeInterval;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String orderId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String type;
}
