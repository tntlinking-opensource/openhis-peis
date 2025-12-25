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
 * 本次职业健康检查复查人员复查情况一览表(FxReviewsituation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:00
 */
@Data
@TableName("FX_REVIEWSITUATION")
@ApiModel(value = "FxReviewsituation", description = "本次职业健康检查复查人员复查情况一览表实体类")
public class FxReviewsituation extends Model<FxReviewsituation> implements Serializable {
    private static final long serialVersionUID = -79384055797494389L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
    private Double age;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "接害因素")
    private String jhysIds;

    @ApiModelProperty(value = "复查情况")
    private String reviewStatus;

    @ApiModelProperty(value = "结论（序列号）")
    private Integer summarySerialno;

    @ApiModelProperty(value = "工种")
    private String trades;
}
