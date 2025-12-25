package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 本次职业健康检查复查人员复查情况一览表(FxReviewsituation)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fx_reviewsituation")
@ApiModel(value = "FxReviewsituation", description = "本次职业健康检查复查人员复查情况一览表实体类")
public class FxReviewsituation extends Model<FxReviewsituation> implements Serializable {
    private static final long serialVersionUID = 544336362910290314L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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
    private String age;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "接害因素")
    private String jhysIds;

    @ApiModelProperty(value = "复查情况(未复查，复查未完成，复查已完成)")
    private String reviewStatus;

    @ApiModelProperty(value = "结论（序列号）")
    private String summarySerialno;

    @ApiModelProperty(value = "工种")
    private String trades;

    public FxReviewsituation(String sampleId, String patientname, String sex, String age, String jhys, String jhysIds, String patientCode, String trades) {
        super();
        this.sampleId = sampleId;
        this.patientname = patientname;
        this.sex = sex;
        this.age = age;
        this.jhys = jhys;
        this.jhysIds = jhysIds;
        this.patientcode = patientCode;
        this.trades = trades;
    }
}
