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
 * (UserHarmClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:43
 */
@Data
@TableName("USER_HARM_CLASS")
@ApiModel(value = "UserHarmClass", description = "$tableInfo.comment实体类")
public class UserHarmClass extends Model<UserHarmClass> implements Serializable {
    private static final long serialVersionUID = 540374799245144194L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String classId;
}
