package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询
 */
@Data
public class FindListInDto implements Serializable {
    private static final long serialVersionUID = -3372450468814807801L;

    @ApiModelProperty(value = "否阳性结果(1阳性  0或NULL非阳性)")
    private String posistive;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @ApiModelProperty(value = "序列号")
    private String serialNo;

    @ApiModelProperty(value = "检查结论")
    private String occupationSummary;
}
