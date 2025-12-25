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
 * 最小套餐分类(MdCreatecomboType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
@Data
@TableName("md_createcombo_type")
@ApiModel(value = "MdCreatecomboType", description = "最小套餐分类实体类")
public class MdCreatecomboType extends Model<MdCreatecomboType> implements Serializable {
    private static final long serialVersionUID = -81503944490884531L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "创建世家")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "序号")
    private Integer typeIndex;

    @ApiModelProperty(value = "状态")
    private Integer typeState;
}
