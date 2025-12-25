package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取套餐详情参数
 */
@Data
public class GetMealDetailsParam implements Serializable {
    private static final long serialVersionUID = -2910383418485593926L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;
}
