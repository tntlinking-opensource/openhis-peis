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
 * (BasMergeConclusion)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:21
 */
@Data
@TableName("BAS_MERGE_CONCLUSION")
@ApiModel(value = "BasMergeConclusion", description = "$tableInfo.comment实体类")
public class BasMergeConclusion extends Model<BasMergeConclusion> implements Serializable {
    private static final long serialVersionUID = 853499516810721625L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionId;

    @ApiModelProperty(value = "${column.comment}")
    private String mergeId;
}
