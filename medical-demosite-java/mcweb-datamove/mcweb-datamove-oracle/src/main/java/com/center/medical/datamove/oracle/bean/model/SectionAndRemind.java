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
 * (SectionAndRemind)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:39
 */
@Data
@TableName("SECTION_AND_REMIND")
@ApiModel(value = "SectionAndRemind", description = "$tableInfo.comment实体类")
public class SectionAndRemind extends Model<SectionAndRemind> implements Serializable {
    private static final long serialVersionUID = -61208105301038925L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String remindId;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;
}
