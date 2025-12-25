package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 右侧体检者数据 返回数据
 */
@Data
public class AnalyseInfoVo implements Serializable {
    private static final long serialVersionUID = 8729085324738523575L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "身份证号/护照号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "体检凭证号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "单位名称")
    @ApiModelProperty(value = "公司")
    private String orgName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费项目名称")
    private String feeitem;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "已收费：0.未收费 1.已收费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @ApiModelProperty(value = "科室id")
    private String idKs;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检者类型ID PatienType表")
    private String idPatientclass;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "体检者收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "qj")
    private String qj;

    @ApiModelProperty(value = "cj")
    private String cj;

    @ApiModelProperty(value = "bj")
    private String bj;

    @ApiModelProperty(value = "jj")
    private String jj;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;
}
