package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 职业最终结论 添加参数
 */
@Data
public class ZySaveParam implements Serializable {
    private static final long serialVersionUID = 6453535653660444257L;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
