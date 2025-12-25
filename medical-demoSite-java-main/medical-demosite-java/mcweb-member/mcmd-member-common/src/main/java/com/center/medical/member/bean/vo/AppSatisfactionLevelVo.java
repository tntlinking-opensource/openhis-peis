package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序体检满意度 返回数据
 */
@Data
public class AppSatisfactionLevelVo implements Serializable {
    private static final long serialVersionUID = 2564156660359230328L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Excel(name = "体检类型" ,readConverterExp = "0=个检,1=团检")
    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "预约流程便捷性")
    @ApiModelProperty(value = "问题1")
    private String question1;

    @Excel(name = "体检环境整洁舒适度")
    @ApiModelProperty(value = "问题2")
    private String question2;

    @Excel(name = "医护人员服务态度")
    @ApiModelProperty(value = "问题3")
    private String question3;

    @Excel(name = "检查项目指引清晰度")
    @ApiModelProperty(value = "问题4")
    private String question4;

    @Excel(name = "体检设备先进性")
    @ApiModelProperty(value = "问题5")
    private String question5;

    @Excel(name = "等待时间合理性")
    @ApiModelProperty(value = "问题6")
    private String question6;

    @Excel(name = "体检服务最满意的方面")
    @ApiModelProperty(value = "问题7")
    private String question7;

    @Excel(name = "需要改进的地方")
    @ApiModelProperty(value = "问题8")
    private String question8;

    @Excel(name = "其他建议或意见")
    @ApiModelProperty(value = "问题9")
    private String question9;

    @Excel(name = "整体满意度")
    @ApiModelProperty(value = "问题10")
    private String question10;

    @Excel(name = "是否会推荐他人选择本中心")
    @ApiModelProperty(value = "问题11")
    private String question11;

    @ApiModelProperty(value = "问题12")
    private String question12;

}
