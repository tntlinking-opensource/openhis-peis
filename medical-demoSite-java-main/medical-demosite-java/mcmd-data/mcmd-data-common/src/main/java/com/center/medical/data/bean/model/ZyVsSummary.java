package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC职业病处理意见(ZyVsSummary)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:19
 */
@Data
@TableName("md_zy_vs_summary")
@ApiModel(value = "ZyVsSummary", description = "JC职业病处理意见实体类")
public class ZyVsSummary extends Model<ZyVsSummary> implements Serializable {
    private static final long serialVersionUID = 932656742855971680L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id号")
    private String id;

    @ApiModelProperty(value = "职业体检类别 0上岗前,1在岗期间,2离岗时,3离岗后,4应急,空未知")
    private String regimentationNote;

    @ApiModelProperty(value = "总结分类")
    private String occupationSummaryClass;

    @ApiModelProperty(value = "总结代码")
    private String occupationSummaryCode;

    @ApiModelProperty(value = "参考依据")
    private String diagnosisFrom;

    @ApiModelProperty(value = "危害因素代码")
    private String occupationDiagnosisCode;

    @ApiModelProperty(value = "危害因素id")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "疾病")
    private String diagnosis;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "对人体的影响")
    private String forPersonInfluence;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "可疑职业病")
    private String occupationDiseast;

    @ApiModelProperty(value = "检查结论")
    private String occupationSummary;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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

    @TableField(exist = false)
    @ApiModelProperty(value = "危害因素名字")
    private String mhHarmName;

    @TableField(exist = false)
    @ApiModelProperty(value = "种类名字")
    private String mzchHarmClass;

    @TableField(exist = false)
    @ApiModelProperty(value = "结论")
    private String mzsOccupationSummary;

    @TableField(exist = false)
    @ApiModelProperty(value = "职业病名称")
    private String occupationDiseastName;

    @TableField(exist = false)
    @ApiModelProperty(value = "收费项目名称")
    private String miExamfeeitemName;
}
