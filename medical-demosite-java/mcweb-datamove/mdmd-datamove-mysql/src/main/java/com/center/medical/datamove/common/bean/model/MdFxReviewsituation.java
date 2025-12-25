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
 * 本次职业健康检查复查人员复查情况一览表(MdFxReviewsituation)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Data
@TableName("md_fx_reviewsituation")
@ApiModel(value = "MdFxReviewsituation", description = "本次职业健康检查复查人员复查情况一览表实体类")
public class MdFxReviewsituation extends Model<MdFxReviewsituation> implements Serializable {
    private static final long serialVersionUID = -89796633212629569L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "检查结果")
    private String positives;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "接害因素")
    private String jhysIds;

    @ApiModelProperty(value = "复查情况(未复查，复查未完成，复查已完成)")
    private String reviewStatus;

    @ApiModelProperty(value = "结论（序列号）")
    private Integer summarySerialno;

    @ApiModelProperty(value = "工种")
    private String trades;
}
