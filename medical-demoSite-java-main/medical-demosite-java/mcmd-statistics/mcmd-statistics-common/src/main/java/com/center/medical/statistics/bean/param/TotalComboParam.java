package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每月每日套餐统计 参数
 */
@Data
public class TotalComboParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8090773716559266569L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;
}
