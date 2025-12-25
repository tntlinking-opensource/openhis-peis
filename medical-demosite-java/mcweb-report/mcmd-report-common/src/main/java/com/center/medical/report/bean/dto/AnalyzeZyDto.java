package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期分布
 */
@Data
public class AnalyzeZyDto implements Serializable {
    private static final long serialVersionUID = 455947945006858847L;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "体检次数")
    private Integer count;

    @ApiModelProperty(value = "男数量")
    private Integer man;

    @ApiModelProperty(value = "女数量")
    private Integer woman;
}
