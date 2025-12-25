package com.center.medical.datamove.common.bean.model;


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
 * TJ综合分析-检出人数（职业）(MdFxDetectionzy)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Data
@TableName("md_fx_detectionzy")
@ApiModel(value = "MdFxDetectionzy", description = "TJ综合分析-检出人数（职业）实体类")
public class MdFxDetectionzy extends Model<MdFxDetectionzy> implements Serializable {
    private static final long serialVersionUID = 936569494651901015L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "疾病名称")
    private String disease;

    @ApiModelProperty(value = "检出人数")
    private Integer checkNum;

    @ApiModelProperty(value = "建议")
    private String occupationDiseast;

    @ApiModelProperty(value = "危害因素ID")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素接触人数")
    private Integer harmNum;
}
