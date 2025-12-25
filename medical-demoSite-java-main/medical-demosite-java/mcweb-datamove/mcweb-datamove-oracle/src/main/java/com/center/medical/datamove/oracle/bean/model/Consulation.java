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
 * (Consulation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:48
 */
@Data
@TableName("CONSULATION")
@ApiModel(value = "Consulation", description = "$tableInfo.comment实体类")
public class Consulation extends Model<Consulation> implements Serializable {
    private static final long serialVersionUID = -18130432639807196L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer consultType;

    @ApiModelProperty(value = "${column.comment}")
    private String consultName;

    @ApiModelProperty(value = "${column.comment}")
    private Date consultTime;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String signPath;

    @ApiModelProperty(value = "${column.comment}")
    private String consultContent;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String consultPhone;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorUsername;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;
}
