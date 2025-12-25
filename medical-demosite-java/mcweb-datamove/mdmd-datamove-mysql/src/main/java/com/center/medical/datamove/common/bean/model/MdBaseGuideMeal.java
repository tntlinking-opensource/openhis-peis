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
 * 基础推荐套餐(MdBaseGuideMeal)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
@Data
@TableName("md_base_guide_meal")
@ApiModel(value = "MdBaseGuideMeal", description = "基础推荐套餐实体类")
public class MdBaseGuideMeal extends Model<MdBaseGuideMeal> implements Serializable {
    private static final long serialVersionUID = -21741091345293188L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建世家")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分中心名称")
    private String fzx;

    @ApiModelProperty(value = "备注")
    private String remark;
}
