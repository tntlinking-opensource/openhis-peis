package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  健康报告 速成 或 生成检验报告
 */
@Data
public class IReportParam implements Serializable {
    private static final long serialVersionUID = 4750411943976452663L;

    @ApiModelProperty(value = "体检号,可以传多个")
    private List<String> patientcode;

    @ApiModelProperty(value = "用户名,可以不传,默认当前登录用户名")
    private String username;

    @ApiModelProperty(value = "健康0 职业1 综合2")
    private Integer dh;

    @ApiModelProperty(value = "是否是检验报告    1检验（普通报告只保留检验科室）")
    private String isJy;

    @ApiModelProperty(value = "是否展现所有图片")
    private String showAllImage;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "0非老人查体（体检系统）1查体档案 2分析报告")
    private Integer lrctType;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "0正式报告 1临时报告")
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "处理的单个体检号")
    private String code;

    @ApiModelProperty(value = "是否跳过检验")
    private Boolean skipVerification = false;
}
