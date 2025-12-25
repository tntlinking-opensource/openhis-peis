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
 * (PacsBasePart)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:39
 */
@Data
@TableName("PACS_BASE_PART")
@ApiModel(value = "PacsBasePart", description = "$tableInfo.comment实体类")
public class PacsBasePart extends Model<PacsBasePart> implements Serializable {
    private static final long serialVersionUID = -24663579396665489L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String partName;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String partSort;

    @ApiModelProperty(value = "${column.comment}")
    private String pid;

    @ApiModelProperty(value = "${column.comment}")
    private String enName;
}
