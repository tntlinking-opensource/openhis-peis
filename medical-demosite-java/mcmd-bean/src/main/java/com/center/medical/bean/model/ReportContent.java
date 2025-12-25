package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 生成报告内容(ReportContent)表实体类
 *
 * @author ay
 * @since 2023-05-18 10:43:56
 */
@Data
@TableName("md_report_content")
@ApiModel(value = "ReportContent", description = "生成报告内容实体类")
public class ReportContent extends Model<ReportContent> implements Serializable {
    private static final long serialVersionUID = -26768941835220516L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "审核者")
    private String checkBy;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "审核时间")
    private Date checkTime;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 7.Pacs报告 8.职业结果告知书 9.检验报告(除检验科的)")
    private Integer reportType;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检类型,0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "审核状态 0.未审核 1.已审核")
    private Integer checkStatus;

    @ApiModelProperty(value = "团价样本ID,md_ball_check_report的id")
    private String analyzeId;

    @ApiModelProperty(value = "对比报告表ID,md_compare_report的id")
    private String compareReportId;

    @ApiModelProperty(value = "科室ID(Pacs报告使用)")
    private String ksId;
    
    @ApiModelProperty(value = "图片")
    private String pic;

}
