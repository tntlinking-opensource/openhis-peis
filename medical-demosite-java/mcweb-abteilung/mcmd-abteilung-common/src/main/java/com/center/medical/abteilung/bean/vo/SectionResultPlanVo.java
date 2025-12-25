package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class SectionResultPlanVo implements Serializable {
    private static final long serialVersionUID = 8563519665558083814L;

    @ApiModelProperty(value = "体检科室")
    private String depName;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "团检单位")
    private String orgName;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;


    @ApiModelProperty(value = "创建人")
    private String creater;


    @ApiModelProperty(value = "状态")
    private String status;


    @ApiModelProperty(value = "审核时间")
    private String auditTime;


    @ApiModelProperty(value = "小结")
    private String conclusions;

}
