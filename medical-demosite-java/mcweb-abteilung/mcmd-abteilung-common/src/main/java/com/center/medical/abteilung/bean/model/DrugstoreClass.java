package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 药品分类(DrugstoreClass)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_drugstore_class")
@ApiModel(value = "DrugstoreClass", description = "药品分类实体类")
public class DrugstoreClass extends Model<DrugstoreClass> implements Serializable {
    private static final long serialVersionUID = -25780468214800775L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "类别编号")
    private String classCode;

    @ApiModelProperty(value = "类别名称")
    private String className;

    @ApiModelProperty(value = "拼音码")
    private String shortCode;
}
