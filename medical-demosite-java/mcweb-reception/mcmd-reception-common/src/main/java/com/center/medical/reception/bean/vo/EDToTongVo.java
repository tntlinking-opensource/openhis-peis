package com.center.medical.reception.bean.vo;

import com.center.medical.reception.bean.dto.EDToTongDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 当日所有检查统收的返回数据
 */
@Data
public class EDToTongVo implements Serializable {
    private static final long serialVersionUID = -7794612983590363946L;

    @ApiModelProperty(value = "总金额")
    private BigDecimal rtotal;

    @ApiModelProperty(value = "数据")
    private List<EDToTongDto> data;
}
