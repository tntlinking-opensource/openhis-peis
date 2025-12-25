package com.center.medical.member.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SecondInterviewParam {

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String personcode;

    @ApiModelProperty(value = "回访结果")
    private String visitResult;

    @ApiModelProperty(value = "回访结果2")
    private Integer secondResult;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;


    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "评价结果 1非常满意 2满意 3 基本满意 4 不满意 0不评价 无 未评价(整个分中心)//1非常满意 2满意4 不满意5取消不满意(科室)")
    private String appraiseResult;
}
