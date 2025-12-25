package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对比报告 返回数据
 */
@Data
public class ContrastReportVo implements Serializable {
    private static final long serialVersionUID = 756206230379477128L;

    @ApiModelProperty(value = "模板0")
    private Map<String, Object> contrast0;

    @ApiModelProperty(value = "模板1")
    private List<HashMap> contrast1;

    @ApiModelProperty(value = "模板2（没用到）")
    private Map<String, Object> contrast2;

    @ApiModelProperty(value = "模板3（没用到）")
    private Map<String, Object> contrast3;

    @ApiModelProperty(value = "模板4（没用到）")
    private Map<String, Object> contrast4;

    @ApiModelProperty(value = "模板5")
    private List<Map> contrast5;
}
