package com.center.medical.workflow.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.workflow.bean.vo.ToBeSubmittedVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 工作流实例记录(WorkflowCase)表实体类
 *
 * @author makejava
 * @since 2023-11-08 16:58:10
 */
@Data
@TableName("md_workflow_case")
@ApiModel(value = "WorkflowCase", description = "工作流实例记录")
public class WorkflowCase extends Model<WorkflowCase> implements Serializable {
    private static final long serialVersionUID = 520816649166553705L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "实例ID")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "关联的业务ID")
    private String bizId;

    @ApiModelProperty(value = "工作流ID")
    private String flowId;

    @ApiModelProperty(value = "实例名称")
    private String caseName;

    @ApiModelProperty(value = "类型标识")
    private String typeFlag;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "驳回的原因")
    private String failText;

    @ApiModelProperty(value = "总进度")
    private Integer allLevel;

    @ApiModelProperty(value = "当前处理进度（已处理完的，0表示尚未开始）")
    private Integer curLevel;

    @ApiModelProperty(value = "状态：-1.失效 0.等待开始 1.进行中 2.已通过 3.被驳回")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "节点状态：0.等待开始(上一级还未通过) 1.进行中(上一级已通过) 2.已通过 3.被驳回")
    private Integer itemStatus;

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

    @ApiModelProperty(value = "根据审批流的情况存一个相应的值，比如业务ID，比如对象等等")
    private String data;

    @ApiModelProperty(value = "分中心")
    @TableField(exist = false)
    private String fzx;

    @ApiModelProperty(value = "类型名称")
    @TableField(exist = false)
    private String typeName;

    @ApiModelProperty(value = "工作流名称")
    @TableField(exist = false)
    private String flowName;

    @ApiModelProperty(value = "节点列表")
    @TableField(exist = false)
    private List<WorkflowCaseItem> itemList;

    @ApiModelProperty(value = "审批会员类型列表")
    @TableField(exist = false)
    private List<ToBeSubmittedVo> reservationTypeList;


    @ApiModelProperty(value = "数据对象")
    @TableField(exist = false)
    private Object dataObject;

    @TableField(exist = false)
    @ApiModelProperty(value = "当前审批人")
    private String userName;

    @TableField(exist = false)
    @ApiModelProperty(value = "审批人id")
    private List<String> approverIds;
}
