package com.center.medical.workflow.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-11-08 13:43
 * @description: 工作流查询参数
 */
@Data
public class WorkflowParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -467190205983664267L;

    @ApiModelProperty(value = "工作流名称")
    private String flowName;

    @ApiModelProperty(value = "类型标识")
    private String typeFlag;

    @ApiModelProperty(value = "状态：0.关闭 1.启用")
    private Integer status;
}
