package com.center.medical.machine.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  //健康体检打印健康报告，职业体检打印职业报告，综合体检两份报告都打印
 *     //生成了就可以打印
 *     //最多显示最近5次
 *     //查询归档表
 */
@Data
public class PersonalReportDto implements Serializable {
    private static final long serialVersionUID = 7919825646934944903L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "登记时间")
    private String date;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


}
