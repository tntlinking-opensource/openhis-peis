package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 综合分析-检出统计、团体小结（健康）(FxDetection)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_detection")
@ApiModel(value = "FxDetection", description = "综合分析-检出统计、团体小结（健康）实体类")
public class FxDetection extends Model<FxDetection> implements Serializable {
    private static final long serialVersionUID = 197782391710191019L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "结论")
    private String conclusion;

    @ApiModelProperty(value = "总体百分比-男")
    private Double checkMale;

    @ApiModelProperty(value = "总体百分比-女")
    private Double checkFemale;

    @ApiModelProperty(value = "总体百分比-合计")
    private Double checkTotal;

    @ApiModelProperty(value = "总体人数-男")
    private Integer allMale;

    @ApiModelProperty(value = "总体人数-女")
    private Integer allFemale;

    @ApiModelProperty(value = "总体人数-合计")
    private Integer allTotal;

    @ApiModelProperty(value = "检出人数-男")
    private Integer detectionMale;

    @ApiModelProperty(value = "检出人数-女")
    private Integer detectionFemale;

    @ApiModelProperty(value = "检出人数-合计")
    private Integer detectionTotal;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "结论ID")
    private String basconclusionId;

    @ApiModelProperty(value = "部门排序")
    private Integer reportSort;
}
