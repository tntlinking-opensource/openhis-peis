package com.center.medical.datamove.oracle.bean.model;


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
 * (PacsItemPart)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:44
 */
@Data
@TableName("PACS_ITEM_PART")
@ApiModel(value = "PacsItemPart", description = "$tableInfo.comment实体类")
public class PacsItemPart extends Model<PacsItemPart> implements Serializable {
    private static final long serialVersionUID = -53224582290781898L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String itemId;

    @ApiModelProperty(value = "${column.comment}")
    private String partId;
}
