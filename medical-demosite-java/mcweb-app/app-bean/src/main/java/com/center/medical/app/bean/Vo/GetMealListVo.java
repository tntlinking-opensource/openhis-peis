package com.center.medical.app.bean.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序获取套餐返回数据
 */
@Data
public class GetMealListVo implements Serializable {
    private static final long serialVersionUID = -4124967692205027727L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型id")
    private String type;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "顺序")
    private Integer seq;

    @ApiModelProperty(value = "体检系统套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "折后价格app(这个字段仅作为参考，使用zhjg)")
    private Double zhjgapp;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "标签名,用英文逗号隔开")
    private String label;
}
