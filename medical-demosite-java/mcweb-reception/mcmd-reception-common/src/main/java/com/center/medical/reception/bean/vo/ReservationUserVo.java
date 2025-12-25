package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 取得已预约客户
 */
@Data
public class ReservationUserVo implements Serializable {
    private static final long serialVersionUID = 933370040619818665L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "预计体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "创建人")
    private Date creater;

    @ApiModelProperty(value = "预约时间（只保存了日期，没有时分秒）")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "notifyResult")
    private String notifyResult;

    @ApiModelProperty(value = "notifyTime")
    private String notifyTime;

    @ApiModelProperty(value = "复查通知单PDF")
    private Integer isNoticed;


}
