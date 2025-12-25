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
 * 总检结论词(MdBasconclusionNew)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
@Data
@TableName("md_basconclusion_new")
@ApiModel(value = "MdBasconclusionNew", description = "总检结论词实体类")
public class MdBasconclusionNew extends Model<MdBasconclusionNew> implements Serializable {
    private static final long serialVersionUID = 660636353011525304L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "结论名称")
    private String name;

    @ApiModelProperty(value = "疾病解释")
    private String depiction;

    @ApiModelProperty(value = "总检建议")
    private String suggestion;

    @ApiModelProperty(value = "健康建议")
    private String advice;

    @ApiModelProperty(value = "饮食指导")
    private String dietguide;

    @ApiModelProperty(value = "运动指导")
    private String sportguide;

    @ApiModelProperty(value = "健康知识")
    private String knowledge;

    @ApiModelProperty(value = "团体建议")
    private String suggestiongroup;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "是否进总检")
    private Integer isLong;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的：1.是 0或NULL. 不是")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "维护（创建）人")
    private String creater;

    @ApiModelProperty(value = "审核人")
    private String auditer;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "审核状态（null不需要审核,不是总检插入）： 0.未审 1.已审")
    private Integer auditStatus;

    @ApiModelProperty(value = "上传状态（null不需要上传,不是总检插入）：0.未上传  1.已上传（总检插入的上传到线上）")
    private Integer scbz;
}
