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
 * (AppPreno)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:49
 */
@Data
@TableName("APP_PRENO")
@ApiModel(value = "AppPreno", description = "$tableInfo.comment实体类")
public class AppPreno extends Model<AppPreno> implements Serializable {
    private static final long serialVersionUID = -47415468658684476L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String outNum;

    @ApiModelProperty(value = "${column.comment}")
    private String takeNum;

    @ApiModelProperty(value = "${column.comment}")
    private Date startTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date endTime;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;

    @ApiModelProperty(value = "${column.comment}")
    private Date takeTime;
}
