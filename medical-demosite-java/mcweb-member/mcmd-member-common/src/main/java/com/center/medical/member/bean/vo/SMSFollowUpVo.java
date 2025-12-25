package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 回访管理-预约短信回访分页返回数据
 */
@Data
public class SMSFollowUpVo implements Serializable {
    private static final long serialVersionUID = -2436803135479616276L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "上传人")
    private String creater;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "通知状态：0.未通知 1.已通知 2.等待通知 3.通知失败")
    private Integer notifyResult;

    @ApiModelProperty(value = "通知时间")
    private Date notifyTime;

    @ApiModelProperty(value = "是否预约通知：0或null.否 1.是")
    private Integer isNoticed;
}
