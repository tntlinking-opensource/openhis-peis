package com.center.medical.data.bean.model;

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
 * @author 路飞船长
 * @since 2022-11-18 10:27:20
 */
@Data
@TableName("md_items_barcode_class")
@ApiModel(value = "ItemsBarcodeClass", description = "收费项目条码打印分类实体类")
public class ItemsBarcodeClass extends Model<ItemsBarcodeClass> implements Serializable {
    private static final long serialVersionUID = -29049177929629463L;

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
