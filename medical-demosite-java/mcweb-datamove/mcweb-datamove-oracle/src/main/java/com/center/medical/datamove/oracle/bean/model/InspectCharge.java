package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * JC检查项目收费项目关联表(InspectCharge)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:14
 */
@Data
@TableName("INSPECT_CHARGE")
@ApiModel(value = "InspectCharge", description = "JC检查项目收费项目关联表实体类")
public class InspectCharge extends Model<InspectCharge> implements Serializable {
    private static final long serialVersionUID = 723972131773434640L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String chargeId;

    @ApiModelProperty(value = "检查项目ID")
    private String inspectId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除0未删除 1已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Double orderIndex;
}
