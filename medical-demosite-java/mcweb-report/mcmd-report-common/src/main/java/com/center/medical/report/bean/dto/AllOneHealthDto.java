package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取健康头模板所有坐标的值
 */
@Data
public class AllOneHealthDto implements Serializable {
    private static final long serialVersionUID = 3255837039068499335L;

    @ApiModelProperty(value = "体检单位名称")
    private String workunit;

    @ApiModelProperty(value = "团体部门")
    private String department;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "体检套餐名称")
    private String combo;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "图片")
    private String head;

    @ApiModelProperty(value = "档案号")
    private String patientarchive;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "总检建议")
    private String totaloffer;

    @ApiModelProperty(value = "总检医生")
    private String totaldoctor;

    @ApiModelProperty(value = "电话")
    private String hotline;

    @ApiModelProperty(value = "报告创建时间")
    private String reportdate;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "总检医师id")
    private String totaldoctorid;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

}
