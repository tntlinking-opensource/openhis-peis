package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * pacs科室工作量统计分页返回数据
 */
@Data
public class PacsQueryVo implements Serializable {
    private static final long serialVersionUID = 168369374388341547L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "科室")
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private String idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "登记日期")
    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @Excel(name = "体检时间")
    @ApiModelProperty(value = "体检时间")
    private String medicaldate;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "录入人")
    @ApiModelProperty(value = "录入人")
    private String username;

    @Excel(name = "医师")
    @ApiModelProperty(value = "检查师")
    private String examdoctor;

    @Excel(name = "检查时间")
    @ApiModelProperty(value = "检查时间")
    private String examdatetime;

    @Excel(name = "描述")
    @ApiModelProperty(value = "检查结果描述")
    private String examresultdesc;

    @Excel(name = "小结")
    @ApiModelProperty(value = "检查结果总结")
    private String examresultsummary;
}
