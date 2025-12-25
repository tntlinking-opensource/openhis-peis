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
 * (QxBkDictionary)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:42
 */
@Data
@TableName("QX_BK_DICTIONARY")
@ApiModel(value = "QxBkDictionary", description = "$tableInfo.comment实体类")
public class QxBkDictionary extends Model<QxBkDictionary> implements Serializable {
    private static final long serialVersionUID = 253767104159680516L;

    @ApiModelProperty(value = "ID")
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
