package com.center.medical.workflow.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实例节点(WorkflowCaseItem)表实体类
 *
 * @author makejava
 * @since 2023-11-08 16:58:11
 */
@Data
@TableName("md_workflow_case_item")
@ApiModel(value = "WorkflowCaseItem", description = "实例节点实体类")
public class WorkflowCaseItem extends Model<WorkflowCaseItem> implements Serializable {
    private static final long serialVersionUID = 111426062894360472L;

    @TableId(value = "item_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "实例节点ID")
    private String itemId;

    @ApiModelProperty(value = "实例ID")
    private String caseId;

    @ApiModelProperty(value = "节点名称")
    private String itemName;

    @ApiModelProperty(value = "操作人编号")
    private String userNo;

    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "层级标识：0.中间层级 1.第一级 2.最后一级 3.只有一级")
    private Integer levelFlag;

    @ApiModelProperty(value = "通知方式：1.短信 2.公众号 3.小程序 4.APP推送 5.站内信")
    private Integer notifyType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "通知状态：0.待通知 1.已通知")
    private Integer notifyStatus;

    @ApiModelProperty(value = "状态：0.等待开始(上一级还未通过) 1.进行中(上一级已通过) 2.已通过 3.被驳回")
    private Integer status;

    @ApiModelProperty(value = "通知时间")
    private Date notifyTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否总检医生，1是0否")
    private Integer isTotal;
}
