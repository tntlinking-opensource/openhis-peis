package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取结论
 */
@Data
public class ProfessionResultDto implements Serializable {
    private static final long serialVersionUID = 6076589452979998139L;

    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @ApiModelProperty(value = "序列号")
    private String serialNo;

}
