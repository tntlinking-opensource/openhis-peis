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
 * (CustFeedback)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:00
 */
@Data
@TableName("CUST_FEEDBACK")
@ApiModel(value = "CustFeedback", description = "$tableInfo.comment实体类")
public class CustFeedback extends Model<CustFeedback> implements Serializable {
    private static final long serialVersionUID = -67573445263549726L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String type;

    @ApiModelProperty(value = "${column.comment}")
    private String contact;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private String detail;

    @ApiModelProperty(value = "${column.comment}")
    private Date faultdate;

    @ApiModelProperty(value = "${column.comment}")
    private String submited;

    @ApiModelProperty(value = "${column.comment}")
    private String state;

    @ApiModelProperty(value = "${column.comment}")
    private Date submitDate;

    @ApiModelProperty(value = "${column.comment}")
    private String submitId;

    @ApiModelProperty(value = "${column.comment}")
    private String submitName;
}
