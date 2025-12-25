package com.center.medical.workflow.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-11-08 13:43
 * @description: 工作流类型查询参数
 */
@Data
public class WorkflowTypeParam implements Serializable {
    private static final long serialVersionUID = 1697142267974377303L;

    @ApiModelProperty(value = "类型标识")
    private String typeFlag;

    @ApiModelProperty(value = "类型名称")
    private String typeName;
}
