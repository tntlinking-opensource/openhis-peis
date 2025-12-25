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
 * (PacsBasexamltemSign)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:42
 */
@Data
@TableName("PACS_BASEXAMLTEM_SIGN")
@ApiModel(value = "PacsBasexamltemSign", description = "$tableInfo.comment实体类")
public class PacsBasexamltemSign extends Model<PacsBasexamltemSign> implements Serializable {
    private static final long serialVersionUID = -44038118995908182L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String inspectId;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String resultId;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isInSummary;

    @ApiModelProperty(value = "${column.comment}")
    private String bodyInputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String bodyDetail;

    @ApiModelProperty(value = "${column.comment}")
    private String bodyDetailZy;

    @ApiModelProperty(value = "${column.comment}")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "${column.comment}")
    private String otherMutex;

    @ApiModelProperty(value = "${column.comment}")
    private String numMutex;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPositive;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDefault;

    @ApiModelProperty(value = "${column.comment}")
    private String orderindex;

    @ApiModelProperty(value = "${column.comment}")
    private String isInput;
}
