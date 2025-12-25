package com.center.medical.reservation.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 团体预约信息
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@ApiModel(value = "RGroupData", description = "团体预约信息")
public class RGroupData {

    @ApiModelProperty(value = "分中心")
    @Excel(name = "分中心")
    private String fzx;

    @ApiModelProperty(value = "预约日期")
    @Excel(name = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "时间列表")
    @Excel(name = "")
    private List<RGDataTime> timeList;

}
