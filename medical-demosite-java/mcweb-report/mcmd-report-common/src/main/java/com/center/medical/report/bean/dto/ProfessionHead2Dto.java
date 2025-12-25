package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 职业史(上岗前不显示)
 */
@Data
public class ProfessionHead2Dto implements Serializable {
    private static final long serialVersionUID = 8862044315807316067L;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "部门")
    private String branch;

    @ApiModelProperty(value = "工种")
    private String typeOfWork;

    @ApiModelProperty(value = "有害因素")
    private String ocupationHarm;

    @ApiModelProperty(value = "有无防护,0无，1有")
    private String occupationDefend;

}
