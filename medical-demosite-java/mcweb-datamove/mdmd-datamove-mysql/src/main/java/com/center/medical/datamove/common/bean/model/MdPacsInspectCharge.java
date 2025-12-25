package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * PACS-项目检查费用(MdPacsInspectCharge)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
@Data
@TableName("md_pacs_inspect_charge")
@ApiModel(value = "MdPacsInspectCharge", description = "PACS-项目检查费用实体类")
public class MdPacsInspectCharge extends Model<MdPacsInspectCharge> implements Serializable {
    private static final long serialVersionUID = 791792096899435070L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "费用id")
    private String chargeId;

    @ApiModelProperty(value = "检验项目id")
    private String inspectId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "排序")
    private Integer orderIndex;
}
