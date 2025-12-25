package com.center.medical.datamove.common.bean.model;


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
 * 网站字典(WsBkDictionary)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Data
@TableName("ws_bk_dictionary")
@ApiModel(value = "WsBkDictionary", description = "网站字典实体类")
public class WsBkDictionary extends Model<WsBkDictionary> implements Serializable {
    private static final long serialVersionUID = -97642933994456034L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String flg;

    @ApiModelProperty(value = "${column.comment}")
    private String issystem;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String type;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
