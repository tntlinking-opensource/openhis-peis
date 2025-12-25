package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 淮南获取体检者具体信息
 * @author world
 */
@Data
public class GetOhEmployeesDto implements Serializable {
    private static final long serialVersionUID = 7245297910572310968L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "复查体检号（第一次的体检号）")
    private String inpatientno;

    @ApiModelProperty(value = "用人单位名称")
    private String empOrgName;

    @ApiModelProperty(value = "用人单位统一社会信用代码")
    private String empOrgBizCode;

    @ApiModelProperty(value = "体检日期")
    private Date peDate;

    @ApiModelProperty(value = "初复检标识")
    private String peFlag;

    @ApiModelProperty(value = "姓名")
    private String patientName;

    @ApiModelProperty(value = "性别代码")
    private String sexCode;

    @ApiModelProperty(value = "证件类型")
    private String idType;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "身份证号")
    private String empPhone;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "工种-编码")
    private String worktypeId;

    @ApiModelProperty(value = "用工单位名称")
    private String employerEnterpriseName;

    @ApiModelProperty(value = "用工单位统一社会信用代码")
    private String employerCreditCode;

    @ApiModelProperty(value = "报告出具日期")
    private Date preparerDate;

}
