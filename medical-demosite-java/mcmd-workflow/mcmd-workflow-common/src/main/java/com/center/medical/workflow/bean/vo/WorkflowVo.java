package com.center.medical.workflow.bean.vo;

import com.center.medical.workflow.bean.model.WorkflowItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-11-08 13:43
 * @description: 工作流查询参数
 */
@Data
public class WorkflowVo implements Serializable {
    private static final long serialVersionUID = -737265030200640023L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "工作流名称")
    private String flowName;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0.关闭 1.启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "更新人")
    private String modifier;

    @ApiModelProperty(value = "节点列表")
    private List<WorkflowItem> itemList;
}
