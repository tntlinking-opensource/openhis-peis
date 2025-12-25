package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-24 10:56
 * @description: 根据输入码匹配数据列表
 */
@Data
public class ListWithSrmVo implements Serializable {
    private static final long serialVersionUID = 5817193207093636408L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "名称")
    private String name;
}
