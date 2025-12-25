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
 * 收费项目条码打印分类(MdItemsBarcodeClass)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Data
@TableName("md_items_barcode_class")
@ApiModel(value = "MdItemsBarcodeClass", description = "收费项目条码打印分类实体类")
public class MdItemsBarcodeClass extends Model<MdItemsBarcodeClass> implements Serializable {
    private static final long serialVersionUID = 897308786057189940L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String className;
}
