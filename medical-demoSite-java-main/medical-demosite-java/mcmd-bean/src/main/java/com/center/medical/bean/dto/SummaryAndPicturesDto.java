package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取小结及图片数据
 */
@Data
public class SummaryAndPicturesDto implements Serializable {
    private static final long serialVersionUID = 5244299293776463380L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String idPatient;

    @ApiModelProperty(value = "科室id")
    private String idKs;

    @ApiModelProperty(value = "项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "收费项目名称(生成小结时使用)")
    private String itemName;

    @ApiModelProperty(value = "检查结果描述")
    private String examresultdesc;

    @ApiModelProperty(value = "检查结果总结")
    private String examresultsummary;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "检查人用户名")
    private String rummagerName;

    @ApiModelProperty(value = "审核者用户名")
    private String auditName;

    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;


    @ApiModelProperty(value = "pacsResult的检查人")
    private String examdoctor;

    @ApiModelProperty(value = "pacsResult的审核人")
    private String auditDoctor;
}
