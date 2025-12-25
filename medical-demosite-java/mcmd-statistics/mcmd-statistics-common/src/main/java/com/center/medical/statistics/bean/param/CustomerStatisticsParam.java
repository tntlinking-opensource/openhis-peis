package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 来检客户统计 参数
 */
@Data
public class CustomerStatisticsParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8529809841940041668L;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "销售经理名称")
    private String xsjl;

}
