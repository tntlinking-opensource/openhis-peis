package com.center.medical.olddata.bean.model;


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
 * 普通套餐与收费项目关联表(MdMealanditem)表实体类
 *
 * @author ay
 * @since 2023-07-25 22:26:23
 */
@Data
@TableName("md_mealanditem")
@ApiModel(value = "MdMealanditem", description = "普通套餐与收费项目关联表实体类")
public class MdMealanditem extends Model<MdMealanditem> implements Serializable {
    private static final long serialVersionUID = -54221564658111840L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "收费项目ID")
    private String sfxmid;

    @ApiModelProperty(value = "是否备选：0或null.否 1.是")
    private String sfbx;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否复制套餐：0或null.否 1.是")
    private Integer isSystem;

    @ApiModelProperty(value = "优惠价")
    private Double price;

    @ApiModelProperty(value = "排序")
    private Integer itemSort;

    @ApiModelProperty(value = "分组")
    private Integer itemGroup;

    @ApiModelProperty(value = "分组类型：0.组内选 1.组间选 2.组任选")
    private Integer groupType;
}
