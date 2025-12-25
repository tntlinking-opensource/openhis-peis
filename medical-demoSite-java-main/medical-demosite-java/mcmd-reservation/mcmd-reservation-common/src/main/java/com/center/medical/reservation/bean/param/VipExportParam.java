package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * vip及贵宾导出 参数
 */
@Data
public class VipExportParam implements Serializable {
    private static final long serialVersionUID = 5898652190250011530L;

    @ApiModelProperty(value = "预约时间")
    private Date reservationDate;

    @ApiModelProperty(value = "分中心ids,传参时多个则以英文逗号（,）隔开")
    private List<String> branchIds;
}
