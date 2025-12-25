package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检验报告生成参数
 */
@Data
public class inspectReportsParam implements Serializable {
    private static final long serialVersionUID = 6213716342292224306L;

    @ApiModelProperty(value = "体检号集合，可以传多个")
    private List<String> patientCodes;





}
