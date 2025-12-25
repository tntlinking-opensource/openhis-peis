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
 * (AppCoupon)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:25
 */
@Data
@TableName("APP_COUPON")
@ApiModel(value = "AppCoupon", description = "$tableInfo.comment实体类")
public class AppCoupon extends Model<AppCoupon> implements Serializable {
    private static final long serialVersionUID = 535938924248549593L;

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
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private Date begindate;

    @ApiModelProperty(value = "${column.comment}")
    private Date enddate;

    @ApiModelProperty(value = "${column.comment}")
    private String limitxf;

    @ApiModelProperty(value = "${column.comment}")
    private String creater;

    @ApiModelProperty(value = "${column.comment}")
    private String modifyer;
}
