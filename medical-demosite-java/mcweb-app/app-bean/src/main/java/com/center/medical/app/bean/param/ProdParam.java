package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞船长
 * @date: 2024/3/19 09:18
 * @description: 推荐商品筛选条件
 */
@Data
public class ProdParam {

    @ApiModelProperty(value = "机构ID，如分中心ID", required = false)
    private String hospitalId;

    @ApiModelProperty(value = "价格排序：0.不排序 1.从高到低 2.从低到高", required = false)
    private Integer priceSort;
}
