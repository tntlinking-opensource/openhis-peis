package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业复查获取数据
 */
@Data
public class RBListDataVo implements Serializable {
    private static final long serialVersionUID = -5879190086008729611L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "身份证")
    private String idcardno;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "INPATIENTNO")
    private String inpatientno;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "reviewCode")
    private String reviewCode;

    @ApiModelProperty(value = "reviewReg")
    private Integer reviewReg;


}
