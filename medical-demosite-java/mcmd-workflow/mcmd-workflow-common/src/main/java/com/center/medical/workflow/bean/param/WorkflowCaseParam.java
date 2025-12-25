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
public class WorkflowCaseParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 3381758058959364841L;

    @ApiModelProperty(value = "关联的业务ID")
    private String bizId;

    @ApiModelProperty(value = "实例名称")
    private String caseName;

    @ApiModelProperty(value = "类型标识")
    private String typeFlag;

    @ApiModelProperty(value = "状态：0.等待开始 1.进行中 2.已通过 3.被驳回")
    private Integer status;

    @ApiModelProperty(value = "操作人编号")
    private String userNo;

    @ApiModelProperty(value = "操作人编号")
    private String creator;
}
