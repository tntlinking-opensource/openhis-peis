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
 * (PacsInspectCharge)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:43
 */
@Data
@TableName("PACS_INSPECT_CHARGE")
@ApiModel(value = "PacsInspectCharge", description = "$tableInfo.comment实体类")
public class PacsInspectCharge extends Model<PacsInspectCharge> implements Serializable {
    private static final long serialVersionUID = 174341163942913961L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String chargeId;

    @ApiModelProperty(value = "${column.comment}")
    private String inspectId;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Double orderIndex;
}
