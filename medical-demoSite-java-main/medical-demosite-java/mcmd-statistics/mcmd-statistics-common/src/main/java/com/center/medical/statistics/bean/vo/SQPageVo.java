package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康检查拒检补检人员名单 分页返回数据
 */
@Data
public class SQPageVo implements Serializable {
    private static final long serialVersionUID = 2946246298712016907L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "体检单位")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "工种")
    @ApiModelProperty(value = "工种")
    private String trades;

    @Excel(name = "接害工龄")
    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @Excel(name = "职业病危害因素")
    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @Excel(name = "拒检补检项目")
    @ApiModelProperty(value = "收费项目名称")
    private String items;

    @Excel(name = "检查结论")
    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @Excel(name = "处理意见")
    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心名字")
    private String fzx;


}
