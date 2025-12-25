package com.center.medical.reservation.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约分页参数
 */
@Data
public class ReservationPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7324156476169795779L;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private Integer status;

}
