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
 * (DrugstoreClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:27
 */
@Data
@TableName("DRUGSTORE_CLASS")
@ApiModel(value = "DrugstoreClass", description = "$tableInfo.comment实体类")
public class DrugstoreClass extends Model<DrugstoreClass> implements Serializable {
    private static final long serialVersionUID = 883801201283293401L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "类别编号")
    private String classCode;

    @ApiModelProperty(value = "类别名称")
    private String className;

    @ApiModelProperty(value = "拼音码")
    private String shortCode;
}
