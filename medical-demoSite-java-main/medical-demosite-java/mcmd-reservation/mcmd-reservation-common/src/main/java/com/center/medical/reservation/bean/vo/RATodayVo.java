package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取今日预约信息
 */
@Data
public class RATodayVo implements Serializable {
    private static final long serialVersionUID = -2811748715992347137L;


    @ApiModelProperty(value = "今日预约人数")
    private Integer num1;

    @ApiModelProperty(value = "已预约未体检人数")
    private Integer num2;

    @ApiModelProperty(value = "未预约体检人数")
    private Integer num3;
}
