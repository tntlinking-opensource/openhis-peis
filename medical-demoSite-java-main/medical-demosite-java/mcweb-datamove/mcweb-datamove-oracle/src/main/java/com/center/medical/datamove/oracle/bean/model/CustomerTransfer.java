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
 * (CustomerTransfer)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:03
 */
@Data
@TableName("CUSTOMER_TRANSFER")
@ApiModel(value = "CustomerTransfer", description = "$tableInfo.comment实体类")
public class CustomerTransfer extends Model<CustomerTransfer> implements Serializable {
    private static final long serialVersionUID = -17662901362240384L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String fromXsjlid;

    @ApiModelProperty(value = "${column.comment}")
    private String toXsjlid;

    @ApiModelProperty(value = "${column.comment}")
    private String toXsjl;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer xzzt;

    @ApiModelProperty(value = "${column.comment}")
    private Date xzdate;

    @ApiModelProperty(value = "${column.comment}")
    private String customerId;
}
