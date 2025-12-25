package com.center.medical.reception.bean.vo;

import com.center.medical.reception.bean.dto.ToPayWayDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 当日所有检查实收的费用统计返回数据
 */
@Data
public class ToPayWayVo implements Serializable {
    private static final long serialVersionUID = -3360654886607763864L;

    @ApiModelProperty(value = "数据")
    private List<ToPayWayDto> data;

    @ApiModelProperty(value = "总金额")
    private BigDecimal rtotal;
}
