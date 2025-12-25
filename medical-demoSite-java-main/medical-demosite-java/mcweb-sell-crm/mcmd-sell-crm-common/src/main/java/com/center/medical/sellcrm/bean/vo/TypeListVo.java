package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取最小套餐分类返回数据
 */
@Data
public class TypeListVo implements Serializable {
    private static final long serialVersionUID = -5732185078078099007L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String text;
}
