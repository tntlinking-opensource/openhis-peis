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
 * 药品分类(MdDrugstoreClass)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Data
@TableName("md_drugstore_class")
@ApiModel(value = "MdDrugstoreClass", description = "药品分类实体类")
public class MdDrugstoreClass extends Model<MdDrugstoreClass> implements Serializable {
    private static final long serialVersionUID = -30570832229224080L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "类别编号")
    private String classCode;

    @ApiModelProperty(value = "类别名称")
    private String className;

    @ApiModelProperty(value = "拼音码")
    private String shortCode;
}
