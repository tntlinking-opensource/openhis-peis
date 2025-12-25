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
 * (PaPeissortexam)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:37
 */
@Data
@TableName("PA_PEISSORTEXAM")
@ApiModel(value = "PaPeissortexam", description = "$tableInfo.comment实体类")
public class PaPeissortexam extends Model<PaPeissortexam> implements Serializable {
    private static final long serialVersionUID = -86645760771316588L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String branchId;

    @ApiModelProperty(value = "${column.comment}")
    private String areaId;

    @ApiModelProperty(value = "${column.comment}")
    private String sortNum;

    @ApiModelProperty(value = "${column.comment}")
    private Date sortDate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isOpen;
}
