package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (UserSaleer)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:44
 */
@Data
@TableName("USER_SALEER")
@ApiModel(value = "UserSaleer", description = "$tableInfo.comment实体类")
public class UserSaleer extends Model<UserSaleer> implements Serializable {
    private static final long serialVersionUID = 616934543418074528L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String saleerMd5;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
