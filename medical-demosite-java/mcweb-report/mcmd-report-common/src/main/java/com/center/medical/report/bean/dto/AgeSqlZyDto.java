package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  查询年龄
 */
@Data
public class AgeSqlZyDto implements Serializable {
    private static final long serialVersionUID = -7867537416125992269L;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "男数量")
    private Integer man;

    @ApiModelProperty(value = "女数量")
    private Integer woman;

}
