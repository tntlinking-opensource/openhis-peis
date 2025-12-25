package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-项目检查费用(PacsInspectCharge)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_inspect_charge")
@ApiModel(value = "PacsInspectCharge", description = "PACS-项目检查费用实体类")
public class PacsInspectCharge extends Model<PacsInspectCharge> implements Serializable {
    private static final long serialVersionUID = 661781750042655115L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "费用id")
    private String chargeId;

    @ApiModelProperty(value = "检验项目id")
    private String inspectId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "排序")
    private Integer orderIndex;
}
