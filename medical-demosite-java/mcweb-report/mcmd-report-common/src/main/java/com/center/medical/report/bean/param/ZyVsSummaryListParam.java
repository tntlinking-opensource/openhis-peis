package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取职业最终结论 参数
 */
@Data
public class ZyVsSummaryListParam implements Serializable {
    private static final long serialVersionUID = 4190720783208889948L;

    @ApiModelProperty(value = "职业体检类别(0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急)")
    private String regimentationNote;

    @ApiModelProperty(value = "检查结论")
    private List<String> occupationSummary;

    @ApiModelProperty(value = "危害因素IDs")
    private List<String> occupationDiagnosis;
}
