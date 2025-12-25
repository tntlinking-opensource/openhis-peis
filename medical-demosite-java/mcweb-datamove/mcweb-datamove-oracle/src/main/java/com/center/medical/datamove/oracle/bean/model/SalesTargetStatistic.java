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
 * 销售目标自动统计(SalesTargetStatistic)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:30
 */
@Data
@TableName("SALES_TARGET_STATISTIC")
@ApiModel(value = "SalesTargetStatistic", description = "销售目标自动统计实体类")
public class SalesTargetStatistic extends Model<SalesTargetStatistic> implements Serializable {
    private static final long serialVersionUID = -48573840667669395L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String userid;

    @ApiModelProperty(value = "${column.comment}")
    private String username;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete1;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete2;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete3;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete4;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete5;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete6;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete7;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete8;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete9;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete10;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete11;

    @ApiModelProperty(value = "${column.comment}")
    private Double complete12;

    @ApiModelProperty(value = "${column.comment}")
    private String bz;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String year;

    @ApiModelProperty(value = "${column.comment}")
    private Integer type;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
