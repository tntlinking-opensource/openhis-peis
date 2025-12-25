package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/2/23 09:16
 * @description:
 */
@Data
@ApiModel(value = "BusinessSourceParam", description = "合作第三方查询参数")
public class BusinessSourceParam implements Serializable {
    private static final long serialVersionUID = -2358377742747201463L;

    @ApiModelProperty(value = "第三方名称")
    private String typeName;

    @ApiModelProperty(value = "状态：-1.删除 0.关闭 1.开放中")
    private Integer status;

    @ApiModelProperty(value = "第三方ID")
    private String sourceId;
}
