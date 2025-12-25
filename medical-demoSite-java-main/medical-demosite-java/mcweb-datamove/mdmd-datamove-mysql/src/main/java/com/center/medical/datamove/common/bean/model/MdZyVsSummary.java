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
 * JC职业病处理意见(MdZyVsSummary)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
@Data
@TableName("md_zy_vs_summary")
@ApiModel(value = "MdZyVsSummary", description = "JC职业病处理意见实体类")
public class MdZyVsSummary extends Model<MdZyVsSummary> implements Serializable {
    private static final long serialVersionUID = 520414979957543275L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id号")
    private String id;

    @ApiModelProperty(value = "职业体检类别")
    private String regimentationNote;

    @ApiModelProperty(value = "总结分类")
    private String occupationSummaryClass;

    @ApiModelProperty(value = "总结代码")
    private String occupationSummaryCode;

    @ApiModelProperty(value = "参考依据")
    private String diagnosisFrom;

    @ApiModelProperty(value = "危害因素代码")
    private String occupationDiagnosisCode;

    @ApiModelProperty(value = "危害因素")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "疾病")
    private String diagnosis;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "对人体的影响")
    private String forPersonInfluence;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "可疑职业病")
    private String occupationDiseast;

    @ApiModelProperty(value = "检查结论")
    private String occupationSummary;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "危害因素分类分类")
    private String healthEvaluationClass;

    @ApiModelProperty(value = "职业禁忌症代码")
    private String zzjjzdm;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "总结分类ID")
    private String serialNo;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;

    @ApiModelProperty(value = "建议进报告：1进 0不进")
    private Integer alwaysInReport;
}
