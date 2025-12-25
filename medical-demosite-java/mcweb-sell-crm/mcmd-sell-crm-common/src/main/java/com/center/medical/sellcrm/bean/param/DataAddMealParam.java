package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增套餐-体检套餐列表
 */
@Data
public class DataAddMealParam implements Serializable {
    private static final long serialVersionUID = 4112071137002857718L;

    @ApiModelProperty(value = "是否禁用：1.是  0或null.否")
    private String isBan;

    @ApiModelProperty(value = "分中心集合")
    private List<String> cid;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

}
