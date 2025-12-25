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
 * 生成报告内容(MdReportContent)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
@Data
@TableName("md_report_content")
@ApiModel(value = "MdReportContent", description = "生成报告内容实体类")
public class MdReportContent extends Model<MdReportContent> implements Serializable {
    private static final long serialVersionUID = 954592896585410546L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "审核者")
    private String checkBy;

    @ApiModelProperty(value = "审核时间")
    private Date checkTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 7.Pacs报告 8.职业结果告知书")
    private Integer reportType;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检类型,0.健康体检 1.职业体检")
    private String idExamtype;

    @ApiModelProperty(value = "审核状态 0.未审核 1.已审核")
    private Integer checkStatus;

    @ApiModelProperty(value = "团价样本id，md_ball_check_report的id")
    private String analyzeId;

    @ApiModelProperty(value = "对比报告id，md_compareReport的id")
    private String compareReportId;

    @ApiModelProperty(value = "科室id，PACS报告使用")
    private String ksId;
}
