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
 * (Peissortexam)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:33
 */
@Data
@TableName("PEISSORTEXAM")
@ApiModel(value = "Peissortexam", description = "$tableInfo.comment实体类")
public class Peissortexam extends Model<Peissortexam> implements Serializable {
    private static final long serialVersionUID = 642066713636591361L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String groupId;

    @ApiModelProperty(value = "${column.comment}")
    private Date sortDate;

    @ApiModelProperty(value = "${column.comment}")
    private String orderId;

    @ApiModelProperty(value = "${column.comment}")
    private String sortNum;

    @ApiModelProperty(value = "${column.comment}")
    private String tcid;

    @ApiModelProperty(value = "${column.comment}")
    private String am;

    @ApiModelProperty(value = "${column.comment}")
    private String pm;

    @ApiModelProperty(value = "${column.comment}")
    private String creator;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;
}
