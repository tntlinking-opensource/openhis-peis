package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
//个检危险值回访传入参数
public class PeispatientParam implements Serializable {

    private static final long serialVersionUID = 3523341865784618696L;
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = " 客户类型")
    private String khlx;

    @ApiModelProperty(value = " 体检开始时间")
    private Date startDate;

    @ApiModelProperty(value = " 体检结束时间")
    private Date endDate;

    @ApiModelProperty(value = " 阳性结果")
    private String yxjg;

    @ApiModelProperty(value = " 危急程度")
    private String wjzjb;

}
