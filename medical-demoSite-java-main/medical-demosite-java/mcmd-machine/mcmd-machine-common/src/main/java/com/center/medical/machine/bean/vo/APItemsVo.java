package com.center.medical.machine.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 加项缴费-选择体检项目
 */
@Data
public class APItemsVo implements Serializable {
    private static final long serialVersionUID = 3112555151111306877L;

    @ApiModelProperty(value = "临时项目总价")
    private BigDecimal temporaryProjectTotalPrice;

    @ApiModelProperty(value = "体检项目")
    private List<Map> Items;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
