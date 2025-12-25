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
 * (BaseGuideMeal)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:33
 */
@Data
@TableName("BASE_GUIDE_MEAL")
@ApiModel(value = "BaseGuideMeal", description = "$tableInfo.comment实体类")
public class BaseGuideMeal extends Model<BaseGuideMeal> implements Serializable {
    private static final long serialVersionUID = -45233012287350111L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String type;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String value;

    @ApiModelProperty(value = "${column.comment}")
    private String content;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;

    @ApiModelProperty(value = "${column.comment}")
    private String remark;
}
