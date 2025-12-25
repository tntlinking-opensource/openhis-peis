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
 * ZJ总检结论词表(MdTotalVerdict)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:11
 */
@Data
@TableName("md_total_verdict")
@ApiModel(value = "MdTotalVerdict", description = "ZJ总检结论词表实体类")
public class MdTotalVerdict extends Model<MdTotalVerdict> implements Serializable {
    private static final long serialVersionUID = -39647768082048772L;

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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "检查类型：0:健康 1:职业")
    private Integer diseaseHealth;

    @ApiModelProperty(value = "标志：0不出现,1出现")
    private Integer flag;

    @ApiModelProperty(value = "总检建议")
    private String totalAdvice;

    @ApiModelProperty(value = "总检结论词名称")
    private String basconclusionName;

    @ApiModelProperty(value = "合并结伦词ID")
    private String mergeId;

    @ApiModelProperty(value = "总检结伦词合并名称")
    private String mergeName;

    @ApiModelProperty(value = "顺序")
    private Integer verdictSort;

    @ApiModelProperty(value = "团检建议")
    private String suggestiongroup;
}
