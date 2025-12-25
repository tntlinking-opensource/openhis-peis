package com.center.medical.reservation.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 团队预约信息导出——时间列表
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@ApiModel(value = "RGDataTime", description = "团队预约信息导出——时间列表")
public class RGDataTime {

    @ApiModelProperty(value = "时间")
    @Excel(name = "时间", readConverterExp = "0=上午,1=下午")
    private Integer amOrPm;

    @ApiModelProperty(value = "团体列表")
    @Excel(name = "")
    private List<RGDataGroup> item4;

}
