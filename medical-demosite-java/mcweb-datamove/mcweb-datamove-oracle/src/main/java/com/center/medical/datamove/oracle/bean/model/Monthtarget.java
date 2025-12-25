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
 * 销售月度计划(Monthtarget)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:07
 */
@Data
@TableName("MONTHTARGET")
@ApiModel(value = "Monthtarget", description = "销售月度计划实体类")
public class Monthtarget extends Model<Monthtarget> implements Serializable {
    private static final long serialVersionUID = 905512275770768935L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String xsjlid;

    @ApiModelProperty(value = "${column.comment}")
    private String year;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxid;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;

    @ApiModelProperty(value = "${column.comment}")
    private Double target1;

    @ApiModelProperty(value = "${column.comment}")
    private Double target2;

    @ApiModelProperty(value = "${column.comment}")
    private Double target3;

    @ApiModelProperty(value = "${column.comment}")
    private Double target4;

    @ApiModelProperty(value = "${column.comment}")
    private Double target5;

    @ApiModelProperty(value = "${column.comment}")
    private Double target6;

    @ApiModelProperty(value = "${column.comment}")
    private Double target7;

    @ApiModelProperty(value = "${column.comment}")
    private Double target8;

    @ApiModelProperty(value = "${column.comment}")
    private Double target9;

    @ApiModelProperty(value = "${column.comment}")
    private Double target10;

    @ApiModelProperty(value = "${column.comment}")
    private Double target11;

    @ApiModelProperty(value = "${column.comment}")
    private Double target12;
}
