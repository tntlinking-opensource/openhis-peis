package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC检查项目收费项目关联表(MdInspectCharge)表实体类
 *
 * @author ay
 * @since 2024-07-13 13:47:00
 */
@Data
@TableName("md_inspect_charge")
@ApiModel(value = "MdInspectCharge", description = "JC检查项目收费项目关联表实体类")
public class MdInspectCharge extends Model<MdInspectCharge> implements Serializable {
    private static final long serialVersionUID = 985525763861998347L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "顺序")
    private Integer orderIndex;

}
