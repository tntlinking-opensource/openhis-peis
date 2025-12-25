package com.center.medical.workflow.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工作流(Workflow)表实体类
 *
 * @author makejava
 * @since 2023-11-08 16:58:09
 */
@Data
@TableName("md_workflow")
@ApiModel(value = "Workflow", description = "工作流实体类")
public class Workflow extends Model<Workflow> implements Serializable {
    private static final long serialVersionUID = -92598759789403334L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "工作流名称")
    private String flowName;

    @ApiModelProperty(value = "类型标识")
    private String typeFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0.关闭 1.启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "更新人")
    private String modifier;

    @ApiModelProperty(value = "分中心")
    @TableField(exist = false)
    private String fzx;

    @ApiModelProperty(value = "类型名称")
    @TableField(exist = false)
    private String typeName;

    @ApiModelProperty(value = "节点列表")
    @TableField(exist = false)
    private List<WorkflowItem> itemList;
}
