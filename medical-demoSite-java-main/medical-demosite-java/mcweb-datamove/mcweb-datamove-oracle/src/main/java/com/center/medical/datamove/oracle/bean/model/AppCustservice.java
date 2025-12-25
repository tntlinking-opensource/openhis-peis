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
 * (AppCustservice)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:28
 */
@Data
@TableName("APP_CUSTSERVICE")
@ApiModel(value = "AppCustservice", description = "$tableInfo.comment实体类")
public class AppCustservice extends Model<AppCustservice> implements Serializable {
    private static final long serialVersionUID = -82581521940674951L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String serviceId;

    @ApiModelProperty(value = "${column.comment}")
    private String customer;

    @ApiModelProperty(value = "${column.comment}")
    private String deleteFlg;
}
