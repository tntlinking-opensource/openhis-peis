package com.center.medical.workflow.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 待提交的审批 返回数据
 */
@Data
public class ToBeSubmittedVo implements Serializable {
    private static final long serialVersionUID = -807395930324600434L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;

    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

}
