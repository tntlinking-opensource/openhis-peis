package com.center.medical.statistics.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取收费明细 合计费用
 */
@Data
public class FinanceAmountVo implements Serializable {
    private static final long serialVersionUID = -7530939001674064425L;


    @ApiModelProperty(value = "团检合计")
    private String tthj;

    @ApiModelProperty(value = "个检合计")
    private String gjhj;

}
