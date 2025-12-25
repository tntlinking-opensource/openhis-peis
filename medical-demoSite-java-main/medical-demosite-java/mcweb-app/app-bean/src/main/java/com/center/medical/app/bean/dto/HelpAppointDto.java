package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 帮人预约返回数据
 */
@Data
public class HelpAppointDto implements Serializable {
    private static final long serialVersionUID = 8511542375192930373L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约时间")
    private String reservationDate;

    @ApiModelProperty(value = "预约时间段")
    private String timeSlot;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "状态 1待预约,2已预约,3体检中,4体检结束")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

}
