package com.center.medical.machine.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 获取该体检号下的收费项目
 */
@Data
public class GetAllItemsDto implements Serializable {
    private static final long serialVersionUID = 3158196553317207312L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;
}
