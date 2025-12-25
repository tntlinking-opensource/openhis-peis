package com.center.medical.pacs.bean.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接收检查数据
 */
@Data
public class PostCheckExamParam implements Serializable {
    private static final long serialVersionUID = -4508865565067552219L;

    @JsonProperty("Token")
    @ApiModelProperty(value = "Token")
    private String token;

    @JsonProperty("Age")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @JsonProperty("BirthDay")
    @ApiModelProperty(value = "生日")
    private String birthDay;

    @JsonProperty("CheckDate")
    @ApiModelProperty(value = "检查日期")
    private String checkDate;

    @JsonProperty("CheckID")
    @ApiModelProperty(value = "检查号")
    private String checkID;

    @JsonProperty("CheckResult")
    @ApiModelProperty(value = "检查结果 医生建议")
    private String checkResult;

    @JsonProperty("CredentialNumber")
    @ApiModelProperty(value = "证件号")
    private String credentialNumber;

    @JsonProperty("SerialNo")
    @ApiModelProperty(value = "设备编号")
    private String serialNo;

    @JsonProperty("Diagnosis")
    @ApiModelProperty(value = "诊断")
    private String diagnosis;

    @JsonProperty("DiagnosticianDoctor")
    @ApiModelProperty(value = "诊断医生")
    private String diagnosticianDoctor;


    @JsonProperty("ExamDepartment")
    @ApiModelProperty(value = "科室")
    private String examDepartment;

    @JsonProperty("ExamDoctor")
    @ApiModelProperty(value = "医生")
    private String examDoctor;

    @JsonProperty("Gender")
    @ApiModelProperty(value = "性别")
    private String gender;

    @JsonProperty("Name")
    @ApiModelProperty(value = "名字")
    private String name;

    @JsonProperty("Parameters")
    @ApiModelProperty(value = "参数")
    private String parameters;

    @JsonProperty("ParamterName")
    @ApiModelProperty(value = "结果参数名")
    private String paramterName;

    @JsonProperty("PatientID")
    @ApiModelProperty(value = "体检号")
    private String patientID;

    @JsonProperty("Report")
    @ApiModelProperty(value = "报告单base64字符串 png格式")
    private String report;

}
