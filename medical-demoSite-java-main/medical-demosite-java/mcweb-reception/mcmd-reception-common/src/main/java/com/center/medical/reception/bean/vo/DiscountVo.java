package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团体批量折扣或优惠价页面展现返回数据
 */
@Data
public class DiscountVo implements Serializable {
    private static final long serialVersionUID = 1115269493665963072L;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "折扣")
    private Double zk;

    @ApiModelProperty(value = "最大折扣")
    private Double maxzk;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String name;
}
