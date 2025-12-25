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
 * (TTjZyzlqkb)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:11
 */
@Data
@TableName("T_TJ_ZYZLQKB")
@ApiModel(value = "TTjZyzlqkb", description = "$tableInfo.comment实体类")
public class TTjZyzlqkb extends Model<TTjZyzlqkb> implements Serializable {
    private static final long serialVersionUID = -95392996058803129L;

    @ApiModelProperty(value = "${column.comment}")
    private String zId;

    @ApiModelProperty(value = "${column.comment}")
    private String dGrdabh;

    @ApiModelProperty(value = "${column.comment}")
    private String zType;

    @ApiModelProperty(value = "${column.comment}")
    private String zRyjcrq;

    @ApiModelProperty(value = "${column.comment}")
    private String zCyccrq;

    @ApiModelProperty(value = "${column.comment}")
    private String zYuanyin;

    @ApiModelProperty(value = "${column.comment}")
    private String zYljgmc;

    @ApiModelProperty(value = "${column.comment}")
    private String zBingah;

    @ApiModelProperty(value = "${column.comment}")
    private String zHappentime;

    @ApiModelProperty(value = "${column.comment}")
    private String zHappentimeOld;
}
