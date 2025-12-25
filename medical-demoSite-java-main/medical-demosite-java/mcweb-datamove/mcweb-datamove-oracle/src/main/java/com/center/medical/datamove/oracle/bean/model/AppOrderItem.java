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
 * 小程序微信订单项目(AppOrderItem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:48
 */
@Data
@TableName("APP_ORDER_ITEM")
@ApiModel(value = "AppOrderItem", description = "小程序微信订单项目实体类")
public class AppOrderItem extends Model<AppOrderItem> implements Serializable {
    private static final long serialVersionUID = -79678761024741912L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;

    @ApiModelProperty(value = "app_order.id")
    private String orderId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isOptional;
}
