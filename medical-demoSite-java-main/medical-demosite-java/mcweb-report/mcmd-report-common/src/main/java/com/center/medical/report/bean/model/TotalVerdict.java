package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ZJ总检结论词表(TotalVerdict)表实体类
 *
 * @author 路飞船长
 * @since 2022-12-08 09:24:21
 */
@Data
@TableName("md_total_verdict")
@ApiModel(value = "TotalVerdict", description = "ZJ总检结论词表实体类")
public class TotalVerdict extends Model<TotalVerdict> implements Serializable {
    private static final long serialVersionUID = 757246294831550393L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "总检主表ID")
    private String totalId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "检查类型：0:健康 1:职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "标志：0不出现,1出现 跟老系统的see是一个字段")
    private Integer flag;

    @ApiModelProperty(value = "总检建议")
    private String totalAdvice;

    @ApiModelProperty(value = "总检结论词名称 跟老系统的basconclusion是一个字段")
    private String basconclusionName;

    @ApiModelProperty(value = "合并结伦词ID")
    private String mergeId;

    @ApiModelProperty(value = "总检结伦词合并名称")
    private String mergeName;

    @ApiModelProperty(value = "顺序")
    private Integer verdictSort;

    @ApiModelProperty(value = "团检建议,跟老系统tjjy是一个字段")
    private String suggestiongroup;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建人")
    private String creater;

    @TableField(exist = false)
    @ApiModelProperty(value = "科室")
    private String division;
}
