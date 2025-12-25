package com.center.medical.member.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SatisfactionParam implements Serializable {
    private static final long serialVersionUID = -2537954775959556458L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String personcode;

    @ApiModelProperty(value = "登记员")
    private String ksDoctorId;

    @ApiModelProperty(value = "评价结果")
    private String appraiseResult;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "回访结果")
    private String visitResult;


    @ApiModelProperty(value = "用户id")
    private String userid;


    @ApiModelProperty(value = "科室ID")
    private String divisionId;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "登记开始时间")
    private Date startRegTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "登记结束时间")
    private Date endRegTime;


    @ApiModelProperty(value = "电话")
    private String phone;


}
