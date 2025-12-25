package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取职业史信息
 */
@Data
public class ExpHead2Dto implements Serializable {
    private static final long serialVersionUID = -8439426272623205383L;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "截止年月")
    private Date stopDate;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "部门")
    private String branch;

    @ApiModelProperty(value = "工种")
    private String typeOfWork;

    @ApiModelProperty(value = "ocupationHarm")
    private String ocupationHarm;

    @ApiModelProperty(value = "有无防护,0无，1有")
    private String occupationDefend;
}
