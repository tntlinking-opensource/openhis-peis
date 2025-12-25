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
 * 第三方接口关联记录(MdItemRelatedInformation)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
@Data
@TableName("md_item_related_information")
@ApiModel(value = "MdItemRelatedInformation", description = "第三方接口关联记录实体类")
public class MdItemRelatedInformation extends Model<MdItemRelatedInformation> implements Serializable {
    private static final long serialVersionUID = -40269527134687266L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "记录ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID（暂未使用）")
    private String itemId;

    @ApiModelProperty(value = "收费项目编号（暂未使用）")
    private String itemCode;

    @ApiModelProperty(value = "收费项目名称（暂未使用）")
    private String itemName;

    @ApiModelProperty(value = "检查项目Id")
    private String baseItemId;

    @ApiModelProperty(value = "检查项目编号")
    private String baseItemCode;

    @ApiModelProperty(value = "检查项目名称")
    private String baseItemName;

    @ApiModelProperty(value = "科室ID（暂未使用）")
    private String ksId;

    @ApiModelProperty(value = "第三方科室名称（暂未使用）")
    private String interfaceKsCode;

    @ApiModelProperty(value = "第三方检查项目编号")
    private String interfaceItemCode;

    @ApiModelProperty(value = "第三方接口名称")
    private String interfaceName;

    @ApiModelProperty(value = "关联时间")
    private Date createDate;

    @ApiModelProperty(value = "更新时间")
    private Date modifyDate;
}
