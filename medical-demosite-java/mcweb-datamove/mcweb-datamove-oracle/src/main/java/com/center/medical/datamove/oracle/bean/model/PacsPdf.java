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
 * PACS PDF  海康医院使用(PacsPdf)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:47
 */
@Data
@TableName("PACS_PDF")
@ApiModel(value = "PacsPdf", description = "PACS PDF  海康医院使用实体类")
public class PacsPdf extends Model<PacsPdf> implements Serializable {
    private static final long serialVersionUID = -34006778291165696L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String feeitemId;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String pdfUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;
}
