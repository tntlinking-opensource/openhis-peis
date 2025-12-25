package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询预约设置参数 分页返回数据
 */
@Data
public class QueryReservationVo implements Serializable {
    private static final long serialVersionUID = -1083459006557934073L;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

}
