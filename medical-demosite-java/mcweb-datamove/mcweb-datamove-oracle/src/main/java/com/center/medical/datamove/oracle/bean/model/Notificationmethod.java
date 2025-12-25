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
 * 通知方式（领取方式）表(Notificationmethod)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:09
 */
@Data
@TableName("NOTIFICATIONMETHOD")
@ApiModel(value = "Notificationmethod", description = "通知方式（领取方式）表实体类")
public class Notificationmethod extends Model<Notificationmethod> implements Serializable {
    private static final long serialVersionUID = 355887849492538087L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "通知方式")
    private String methodName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "是否删除  0：不删除，1：删除")
    private Double isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String isSendNotice;
}
