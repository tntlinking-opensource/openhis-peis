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
 * (SellOutside)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:48
 */
@Data
@TableName("SELL_OUTSIDE")
@ApiModel(value = "SellOutside", description = "$tableInfo.comment实体类")
public class SellOutside extends Model<SellOutside> implements Serializable {
    private static final long serialVersionUID = 237206289252434073L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;

    @ApiModelProperty(value = "${column.comment}")
    private String kh;

    @ApiModelProperty(value = "${column.comment}")
    private String allprice;

    @ApiModelProperty(value = "${column.comment}")
    private String zk;

    @ApiModelProperty(value = "${column.comment}")
    private String sj;

    @ApiModelProperty(value = "${column.comment}")
    private String items;

    @ApiModelProperty(value = "${column.comment}")
    private String sellman;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
