package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取需要报告赋码的数据
 */
@Data
public class NeedReportCodingDataDto implements Serializable {
    private static final long serialVersionUID = -8559179186036124652L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;
}
