package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class AdditionProcessingVo implements Serializable {

    private static final long serialVersionUID = 2236114359514601525L;
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别0男1女")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "团体")
    private String orgName;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "项目科室")
    private String ksname;

    @ApiModelProperty(value = "加项医师")
    private String jxys;

    @ApiModelProperty(value = "登记人")
    private String user;

    @ApiModelProperty(value = "创建日期")
    private String createdate;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "处理状态")
    private String status;

    @ApiModelProperty(value = "处理人")
    private String handleNameId;

    @ApiModelProperty(value = "处理意见")
    private String idea;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "处理日期")
    private String handleTime;

    @ApiModelProperty(value = "创建人")
    private String creater;
}
