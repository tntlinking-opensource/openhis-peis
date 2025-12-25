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
 * (PacsSectionResultTwo)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:54
 */
@Data
@TableName("PACS_SECTION_RESULT_TWO")
@ApiModel(value = "PacsSectionResultTwo", description = "$tableInfo.comment实体类")
public class PacsSectionResultTwo extends Model<PacsSectionResultTwo> implements Serializable {
    private static final long serialVersionUID = 607127651056674883L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String mainId;

    @ApiModelProperty(value = "${column.comment}")
    private String verdictId;

    @ApiModelProperty(value = "${column.comment}")
    private String nodule;

    @ApiModelProperty(value = "${column.comment}")
    private Integer posistive;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isUnchecked;

    @ApiModelProperty(value = "${column.comment}")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "${column.comment}")
    private String basconclusionId;

    @ApiModelProperty(value = "${column.comment}")
    private String divisionId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String chargesId;

    @ApiModelProperty(value = "${column.comment}")
    private String ms;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDanger;

    @ApiModelProperty(value = "${column.comment}")
    private String inputResult;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tjlx;

    @ApiModelProperty(value = "${column.comment}")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private String feeitemId;
}
