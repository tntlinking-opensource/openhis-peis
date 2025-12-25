package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 不合格样品回访，回访时跳出的展示页
 */
@Data
public class BelowSampleEditVo implements Serializable {

    private static final long serialVersionUID = 3368676050862870854L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检时间")
    private Date dateregister;

    @ApiModelProperty(value = "不合格样品数量")
    private String count;

    @ApiModelProperty(value = "不合格原因")
    private String belowquestion;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "再通知")
    private String noticeAgain;

    @ApiModelProperty(value = "回访备注")
    private String memo;


}
