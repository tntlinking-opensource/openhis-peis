package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//不合格样品回访页面传参实体类
@Data
public class BelowSampleParam implements Serializable {

    private static final long serialVersionUID = 4296046029348629336L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = " 体检开始时间")
    private Date startDate;

    @ApiModelProperty(value = " 体检结束时间")
    private Date endDate;

    @ApiModelProperty(value = "不合格原因")
    private String belowquestion;

    @ApiModelProperty(value = "体检分类")
    private String useCodeHiden;

    @ApiModelProperty(value = "完成状态")
    private String status;

}
