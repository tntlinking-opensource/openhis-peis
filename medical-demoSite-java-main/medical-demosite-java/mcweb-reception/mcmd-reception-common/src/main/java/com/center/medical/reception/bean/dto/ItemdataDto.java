package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItemdataDto implements Serializable {
    private static final long serialVersionUID = -5115788904456319216L;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "套餐")
    private String examsuiteName;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "身份证")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "复查体检号")
    private String reviewCode;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "复查状态")
    private String reviewReg;


}
