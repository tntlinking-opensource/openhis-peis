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
 * 第三方项目接口对接信息表(ItemRelatedInformation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:18
 */
@Data
@TableName("ITEM_RELATED_INFORMATION")
@ApiModel(value = "ItemRelatedInformation", description = "第三方项目接口对接信息表实体类")
public class ItemRelatedInformation extends Model<ItemRelatedInformation> implements Serializable {
    private static final long serialVersionUID = 762718280744256058L;

    @ApiModelProperty(value = "修改日期")
    private Date modifyDate;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "收费项目Id")
    private String itemId;

    @ApiModelProperty(value = "体检项目Id")
    private String baseItemId;

    @ApiModelProperty(value = "收费项目编号")
    private String itemCode;

    @ApiModelProperty(value = "体检项目编号")
    private String baseItemCode;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "体检项目名称")
    private String baseItemName;

    @ApiModelProperty(value = "科室ID")
    private String ksId;

    @ApiModelProperty(value = "第三方科室编号")
    private String interfaceKsCode;

    @ApiModelProperty(value = "第三方接口编码")
    private String interfaceItemCode;

    @ApiModelProperty(value = "第三方名称")
    private String interfaceName;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;
}
