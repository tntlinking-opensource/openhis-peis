package com.center.medical.reservation.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-28 13:43
 * @description: 体检系统可预约时间段
 */
@Data
public class ReservationDateParam implements Serializable {

    private static final long serialVersionUID = 2567148958322428395L;

    @ApiModelProperty(value = "分中心ID", required = true)
    private String branchId;

    @ApiModelProperty(value = "起始预约日期", required = true)
//    @NotNull(message = "请先选择起始日期!")
    private Date startDate;

    @ApiModelProperty(value = "截至预约日期", required = true)
//    @NotNull(message = "请先选择截至日期!")
    private Date endDate;
}
