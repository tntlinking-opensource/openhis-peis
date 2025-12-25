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
 * 饮酒种类(MdDrinkingType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
@Data
@TableName("md_drinking_type")
@ApiModel(value = "MdDrinkingType", description = "饮酒种类实体类")
public class MdDrinkingType extends Model<MdDrinkingType> implements Serializable {
    private static final long serialVersionUID = 739438766198795628L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "种类名称")
    private String typeName;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
