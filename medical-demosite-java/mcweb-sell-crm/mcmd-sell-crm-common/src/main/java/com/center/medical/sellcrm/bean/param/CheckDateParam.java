package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检查数据参数
 */
@Data
public class CheckDateParam implements Serializable {
    private static final long serialVersionUID = -70892393347883333L;


    @ApiModelProperty(value = "多个分中心，英文逗号分割")
    private String fzxs;

    @ApiModelProperty(value = "计划检期从")
    private String jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private String jhjqd;

    @ApiModelProperty(value = "id")
    private String id;
}
