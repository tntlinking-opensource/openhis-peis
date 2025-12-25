package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC检查项目收费项目关联表(InspectCharge)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_inspect_charge")
@ApiModel(value = "InspectCharge", description = "JC检查项目收费项目关联表实体类")
public class InspectCharge extends Model<InspectCharge> implements Serializable {
    private static final long serialVersionUID = -34412031606942213L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String chargeId;

    @ApiModelProperty(value = "检查项目ID")
    private String inspectId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "顺序")
    private Integer orderIndex;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String _state;
}
