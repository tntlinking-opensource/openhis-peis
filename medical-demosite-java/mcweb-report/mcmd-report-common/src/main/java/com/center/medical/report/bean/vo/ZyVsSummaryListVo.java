package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取职业最终结论
 */
@Data
public class ZyVsSummaryListVo implements Serializable {
    private static final long serialVersionUID = 4905737698274828593L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "职业体检类别(0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急)")
    private String regimentationNote;

    @ApiModelProperty(value = "检查结论")
    private String occupationSummary;

    @ApiModelProperty(value = "危害因素")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "疾病")
    private String zyjjzdm;

    @ApiModelProperty(value = "职业病名称")
    private String kyzyb;
}
