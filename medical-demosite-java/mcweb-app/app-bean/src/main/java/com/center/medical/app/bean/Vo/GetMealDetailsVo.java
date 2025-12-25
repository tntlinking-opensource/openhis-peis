package com.center.medical.app.bean.Vo;

import com.center.medical.app.bean.dto.MealItemsDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取详情返回数据
 */
@Data
public class GetMealDetailsVo implements Serializable {
    private static final long serialVersionUID = 7978428293855671213L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "类型id")
    private String type;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "标签名,用英文逗号隔开")
    private String label;

    @ApiModelProperty(value = "图片路径，多张以,隔开")
    private String imgUrl;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "体检系统套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "折后价格app(这个字段仅作为参考，使用zhjg)")
    private Double zhjgapp;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "分中心名称")
    private String fzxName;

    @ApiModelProperty(value = "体检地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "收费项目设置")
    private List<MealItemsDto> itemData;
}
