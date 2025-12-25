package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约来检情况分析 右侧分页参数
 */
@Data
public class RightPageParam implements Serializable {
    private static final long serialVersionUID = -3252857644161159793L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "类型：1已预约来检、2已预约未检、3未预约体检人数")
    private Integer type;

}
