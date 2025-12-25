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
 * (SectionResultPlan)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:43
 */
@Data
@TableName("SECTION_RESULT_PLAN")
@ApiModel(value = "SectionResultPlan", description = "$tableInfo.comment实体类")
public class SectionResultPlan extends Model<SectionResultPlan> implements Serializable {
    private static final long serialVersionUID = 177938374988928577L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String creater;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private String errorMsg;
}
