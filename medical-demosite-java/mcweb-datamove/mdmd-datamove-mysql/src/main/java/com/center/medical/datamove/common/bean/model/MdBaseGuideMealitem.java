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
 * 基础收费项目(MdBaseGuideMealitem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Data
@TableName("md_base_guide_mealitem")
@ApiModel(value = "MdBaseGuideMealitem", description = "基础收费项目实体类")
public class MdBaseGuideMealitem extends Model<MdBaseGuideMealitem> implements Serializable {
    private static final long serialVersionUID = -82155711457088862L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "推荐套餐id")
    private String mealId;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "原始价格")
    private Double ysPrice;

    @ApiModelProperty(value = "优惠价格")
    private Double price;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否推荐：1推荐")
    private String tj;

    @ApiModelProperty(value = "排序")
    private Integer orderindex;
}
