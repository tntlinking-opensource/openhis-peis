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
 * (PacsSectionResultMain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:53
 */
@Data
@TableName("PACS_SECTION_RESULT_MAIN")
@ApiModel(value = "PacsSectionResultMain", description = "$tableInfo.comment实体类")
public class PacsSectionResultMain extends Model<PacsSectionResultMain> implements Serializable {
    private static final long serialVersionUID = -72330398539730036L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String rummagerId;

    @ApiModelProperty(value = "${column.comment}")
    private String writeId;

    @ApiModelProperty(value = "${column.comment}")
    private Date rummagerTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date writeTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isAudit;

    @ApiModelProperty(value = "${column.comment}")
    private String auditId;

    @ApiModelProperty(value = "${column.comment}")
    private Date auditTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDanager;

    @ApiModelProperty(value = "${column.comment}")
    private Integer danagerLevel;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusions;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String auditName;

    @ApiModelProperty(value = "${column.comment}")
    private String rummagerName;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isFinish;

    @ApiModelProperty(value = "${column.comment}")
    private String zyConclusions;

    @ApiModelProperty(value = "${column.comment}")
    private String associativeTable;

    @ApiModelProperty(value = "${column.comment}")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private String fileType;

    @ApiModelProperty(value = "${column.comment}")
    private String filePath;

    @ApiModelProperty(value = "${column.comment}")
    private String description;
}
