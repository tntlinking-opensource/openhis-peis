package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC检查项目收费项目关联表(InspectCharge)表实体类
 *
 * @author ay
 * @since 2024-07-13 14:27:29
 */
@Data
@TableName("INSPECT_CHARGE")
@ApiModel(value = "InspectCharge", description = "JC检查项目收费项目关联表实体类")
public class OrInspectCharge extends Model<OrInspectCharge> implements Serializable {
    private static final long serialVersionUID = -91069083142958892L;

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
    private Integer orderIndex;

}
