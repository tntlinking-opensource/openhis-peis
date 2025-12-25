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
 * (OtherReport)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:28
 */
@Data
@TableName("OTHER_REPORT")
@ApiModel(value = "OtherReport", description = "$tableInfo.comment实体类")
public class OtherReport extends Model<OtherReport> implements Serializable {
    private static final long serialVersionUID = -60882654163989064L;

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
    private String pdfUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer createStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String errorMsg;

    @ApiModelProperty(value = "${column.comment}")
    private String creater;

    @ApiModelProperty(value = "${column.comment}")
    private Date createTime;

    @ApiModelProperty(value = "${column.comment}")
    private String reportType;
}
