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
 * 本次体检异常结果检出统计(FxDetection)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:48
 */
@Data
@TableName("FX_DETECTION")
@ApiModel(value = "FxDetection", description = "本次体检异常结果检出统计实体类")
public class FxDetection extends Model<FxDetection> implements Serializable {
    private static final long serialVersionUID = 317202556940183779L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String sampleId;

    @ApiModelProperty(value = "${column.comment}")
    private String depName;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String checkMale;

    @ApiModelProperty(value = "${column.comment}")
    private String checkFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String checkTotal;

    @ApiModelProperty(value = "${column.comment}")
    private String allMale;

    @ApiModelProperty(value = "${column.comment}")
    private String allFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String allTotal;

    @ApiModelProperty(value = "${column.comment}")
    private String detectionMale;

    @ApiModelProperty(value = "${column.comment}")
    private String detectionFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String detectionTotal;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String patientname;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sex;

    @ApiModelProperty(value = "${column.comment}")
    private String age;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepart;

    @ApiModelProperty(value = "${column.comment}")
    private String basconclusionId;

    @ApiModelProperty(value = "${column.comment}")
    private String reportSort;
}
