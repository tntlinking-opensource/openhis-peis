package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取所有套餐 返回数据
 */
@Data
public class AllComboMealVo implements Serializable {
    private static final long serialVersionUID = 8638201403505270590L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;
}
