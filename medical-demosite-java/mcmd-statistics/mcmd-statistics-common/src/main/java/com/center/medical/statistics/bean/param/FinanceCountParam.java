package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检费用统计-收费明细 参数
 */
@Data
public class FinanceCountParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -185626823303968279L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


}
