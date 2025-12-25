package com.center.medical.report.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团检报告审批导出数据
 */
@Data
public class RAExportDataVo implements Serializable {
    private static final long serialVersionUID = -155012530483493143L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @Excel(name = "团体部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "阳性结果")
    @ApiModelProperty(value = "阴性结果")
    private String positiveResult;

    @Excel(name = "结论词")
    @ApiModelProperty(value = "结论")
    private String verdict;

    @Excel(name = "总检建议")
    @ApiModelProperty(value = "总检建议")
    private String offer;

}
