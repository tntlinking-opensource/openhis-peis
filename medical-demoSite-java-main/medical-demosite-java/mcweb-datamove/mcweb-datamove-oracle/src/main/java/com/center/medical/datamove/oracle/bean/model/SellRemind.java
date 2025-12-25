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
 * (SellRemind)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:49
 */
@Data
@TableName("SELL_REMIND")
@ApiModel(value = "SellRemind", description = "$tableInfo.comment实体类")
public class SellRemind extends Model<SellRemind> implements Serializable {
    private static final long serialVersionUID = 722837594165363199L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String khdwid;

    @ApiModelProperty(value = "${column.comment}")
    private String khdwmc;

    @ApiModelProperty(value = "${column.comment}")
    private Date remindTime;

    @ApiModelProperty(value = "${column.comment}")
    private String content;

    @ApiModelProperty(value = "${column.comment}")
    private String createName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;
}
