package com.center.medical.enterprise.bean.vo;

import com.center.medical.enterprise.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出职业数据
 */
@Data
public class ReportExportZyVo implements Serializable {
    private static final long serialVersionUID = -3363181447937418802L;



    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name="登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "职业体检状态，详见：ExamStatus")
    private Integer zytjzt;

    @Excel(name = "报告状态")
    @ApiModelProperty(value = "报告状态")
    private String tjzt;

    @Excel(name = "总检结论")
    @ApiModelProperty(value = "总检结论")
    private String verdict;

    @Excel(name = "检查结果")
    @ApiModelProperty(value = "阳性结果")
    private String posistive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "检查结论")
    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @Excel(name = "处理意见")
    @ApiModelProperty(value = "处理意见")
    private String summaryText;
}
