package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询预约设置参数 分页返回参数
 */
@Data
public class QueryReservationParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 818887690600744752L;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;


    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private Integer status;


    @ApiModelProperty(value = "预约日期(只有查询预约时间的时候，才把这个参数带过去，查询预约日期时不需要这个参数)")
    private Date reservationDate;

}
