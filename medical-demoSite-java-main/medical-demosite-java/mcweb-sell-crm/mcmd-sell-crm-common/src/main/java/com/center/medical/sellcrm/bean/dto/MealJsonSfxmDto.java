package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增套餐下方收费项目
 */
@Data
public class MealJsonSfxmDto implements Serializable {

    private static final long serialVersionUID = 3966040173122189233L;

    @ApiModelProperty(value = "id(需要)")
    private String id;

    @ApiModelProperty(value = "排序(需要)")
    private String sort;

    @ApiModelProperty(value = "分组(需要)")
    private String group;

    @ApiModelProperty(value = "分组类型：0.组内选 1.组间选 2.组任选(需要)")
    private String groupType;

    @ApiModelProperty(value = "优惠价(需要)")
    private String yhj;

    @ApiModelProperty(value = "是否备选：0.否 1.是(需要)")
    private Integer sfbx;

}
