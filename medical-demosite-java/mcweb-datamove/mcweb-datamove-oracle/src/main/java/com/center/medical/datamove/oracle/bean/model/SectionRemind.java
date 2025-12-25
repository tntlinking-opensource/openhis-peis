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
 * (SectionRemind)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:41
 */
@Data
@TableName("SECTION_REMIND")
@ApiModel(value = "SectionRemind", description = "$tableInfo.comment实体类")
public class SectionRemind extends Model<SectionRemind> implements Serializable {
    private static final long serialVersionUID = 332360619584073668L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String remindContent;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String depName;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String userName;

    @ApiModelProperty(value = "${column.comment}")
    private Date remindTime;

    @ApiModelProperty(value = "${column.comment}")
    private String depIds;
}
