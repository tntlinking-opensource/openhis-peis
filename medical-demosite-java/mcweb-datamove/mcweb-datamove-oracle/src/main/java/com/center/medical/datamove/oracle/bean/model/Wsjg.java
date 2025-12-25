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
 * (Wsjg)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:47
 */
@Data
@TableName("WSJG")
@ApiModel(value = "Wsjg", description = "$tableInfo.comment实体类")
public class Wsjg extends Model<Wsjg> implements Serializable {
    private static final long serialVersionUID = 495470264526564062L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "0未删除 1 已删除")
    private Integer isDelete;
}
