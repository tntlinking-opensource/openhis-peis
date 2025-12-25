package com.center.medical.workflow.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作流类型(WorkflowType)表实体类
 *
 * @author makejava
 * @since 2023-11-08 16:58:12
 */
@Data
@TableName("md_workflow_type")
@ApiModel(value = "WorkflowType", description = "工作流类型实体类")
public class WorkflowType extends Model<WorkflowType> implements Serializable {
    private static final long serialVersionUID = 780530145295822008L;

    @TableId(value = "type_id")
    @ApiModelProperty(value = "类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "类型标识")
    private String typeFlag;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}
