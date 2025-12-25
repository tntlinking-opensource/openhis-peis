package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 待领取报告分页返回数据
 */
@Data
public class ReportRemindVo implements Serializable {
    private static final long serialVersionUID = -560313673069122685L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;

    @ApiModelProperty(value = "交出时间")
    private Date joinTime;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;
}
