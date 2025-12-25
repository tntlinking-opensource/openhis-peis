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
 * 小程序预约数量上限(AppReservationNumber)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:58
 */
@Data
@TableName("APP_RESERVATION_NUMBER")
@ApiModel(value = "AppReservationNumber", description = "小程序预约数量上限实体类")
public class AppReservationNumber extends Model<AppReservationNumber> implements Serializable {
    private static final long serialVersionUID = 622917750521729109L;

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
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String groupUsed;

    @ApiModelProperty(value = "${column.comment}")
    private String personUsed;

    @ApiModelProperty(value = "${column.comment}")
    private String am;

    @ApiModelProperty(value = "${column.comment}")
    private String pm;
}
