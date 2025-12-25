package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费项目条码打印分类(ItemsBarcodeClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:23
 */
@Data
@TableName("ITEMS_BARCODE_CLASS")
@ApiModel(value = "ItemsBarcodeClass", description = "收费项目条码打印分类实体类")
public class ItemsBarcodeClass extends Model<ItemsBarcodeClass> implements Serializable {
    private static final long serialVersionUID = -88733896424184144L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String className;
}
