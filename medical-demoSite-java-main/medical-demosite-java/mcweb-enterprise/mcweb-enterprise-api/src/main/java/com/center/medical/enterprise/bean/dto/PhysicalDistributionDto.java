package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检分布 返回数据
 */
@Data
public class PhysicalDistributionDto implements Serializable {
    private static final long serialVersionUID = 7500572367044026494L;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "星期几")
    private String week;

    @ApiModelProperty(value = "总数量")
    private String total;

    @ApiModelProperty(value = "男数量")
    private String male;

    @ApiModelProperty(value = "女数量")
    private String female;

}
