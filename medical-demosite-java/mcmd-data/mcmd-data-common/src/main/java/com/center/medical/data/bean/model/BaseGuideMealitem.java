package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础收费项目(BaseGuideMealitem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_base_guide_mealitem")
@ApiModel(value = "BaseGuideMealitem", description = "基础收费项目实体类")
public class BaseGuideMealitem extends Model<BaseGuideMealitem> implements Serializable {
    private static final long serialVersionUID = 435966229837124613L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "推荐套餐id")
    private String mealId;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
