/*
 * Copyright (c) 2021-2999 沃德国际 All rights reserved.
 *
 * https://www.maiwd.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductDto {
    /**
     * 店铺ID
     */
    @ApiModelProperty(value = "店铺ID", required = true)
    private Long shopId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    private Long prodId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String prodName;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格", required = true)
    private Double price;

    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价", required = true)
    private Double oriPrice;

    /**
     * 库存量
     */
    @ApiModelProperty(value = "库存量", required = true)
    private Integer totalStocks;

    /**
     * 简要描述,卖点等
     */
    @ApiModelProperty(value = "简要描述,卖点等", required = true)
    private String brief;

    /**
     * 状态
     */
    @ApiModelProperty(value = "0:下架、1:上架", required = true)
    private Integer status;

    /**
     * 商品主图
     */
    @ApiModelProperty(value = "商品主图", required = true)
    private String pic;

    @ApiModelProperty(value = "商品图片列表，以逗号分割", required = true)
    private String imgs;

    @ApiModelProperty(value = "商品类型(0普通商品 1拼团 2秒杀 3积分)")
    private Integer prodType;

    /**
     * 商品积分价格
     */
    @ApiModelProperty(value = "商品积分价格")
    private Long scorePrice;

    /**
     * 是否推荐
     */
    private Short isRecommend;

    /**
     * 是否限购
     */
    private Short isLimit;

    /**
     * 单人最多可购买的次数：0为不限
     */
    @ApiModelProperty(value = "单次最多可购买的数量")
    private Integer buyTimes;

    /**
     * 单次最多可购买的数量：0为不限
     */
    @ApiModelProperty(value = "单次最多可购买的数量")
    private Integer buyNum;


}
