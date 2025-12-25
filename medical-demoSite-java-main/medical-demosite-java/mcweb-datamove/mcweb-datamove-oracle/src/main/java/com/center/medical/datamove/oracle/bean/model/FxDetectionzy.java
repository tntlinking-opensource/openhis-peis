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
 * TJ综合分析-检出人数（职业）(FxDetectionzy)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:50
 */
@Data
@TableName("FX_DETECTIONZY")
@ApiModel(value = "FxDetectionzy", description = "TJ综合分析-检出人数（职业）实体类")
public class FxDetectionzy extends Model<FxDetectionzy> implements Serializable {
    private static final long serialVersionUID = -21592883854480989L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
    private String checkNum;

    @ApiModelProperty(value = "建议")
    private String occupationDiseast;

    @ApiModelProperty(value = "危害因素ID")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素接触人数")
    private String harmNum;
}
