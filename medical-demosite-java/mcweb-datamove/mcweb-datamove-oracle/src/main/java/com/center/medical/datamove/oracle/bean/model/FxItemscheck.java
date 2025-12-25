package com.center.medical.datamove.oracle.bean.model;


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
 * (FxItemscheck)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:52
 */
@Data
@TableName("FX_ITEMSCHECK")
@ApiModel(value = "FxItemscheck", description = "$tableInfo.comment实体类")
public class FxItemscheck extends Model<FxItemscheck> implements Serializable {
    private static final long serialVersionUID = 946419785851600278L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String sampleId;

    @ApiModelProperty(value = "${column.comment}")
    private String depName;

    @ApiModelProperty(value = "${column.comment}")
    private String depId;

    @ApiModelProperty(value = "${column.comment}")
    private String itemName;

    @ApiModelProperty(value = "${column.comment}")
    private String itemId;

    @ApiModelProperty(value = "${column.comment}")
    private String checkMale;

    @ApiModelProperty(value = "${column.comment}")
    private String checkFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String checkTotal;

    @ApiModelProperty(value = "${column.comment}")
    private String allMale;

    @ApiModelProperty(value = "${column.comment}")
    private String allFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String allTotal;

    @ApiModelProperty(value = "${column.comment}")
    private String perMale;

    @ApiModelProperty(value = "${column.comment}")
    private String perFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String perTotal;

    @ApiModelProperty(value = "${column.comment}")
    private String reportSort;

    @ApiModelProperty(value = "${column.comment}")
    private String rowno;
}
