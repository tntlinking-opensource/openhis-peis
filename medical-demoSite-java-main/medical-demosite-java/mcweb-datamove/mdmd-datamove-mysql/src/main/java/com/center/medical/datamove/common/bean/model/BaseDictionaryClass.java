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
 * 字典类型(BaseDictionaryClass)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:56
 */
@Data
@TableName("base_dictionary_class")
@ApiModel(value = "BaseDictionaryClass", description = "字典类型实体类")
public class BaseDictionaryClass extends Model<BaseDictionaryClass> implements Serializable {
    private static final long serialVersionUID = -69722231380375140L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String className;

    @ApiModelProperty(value = "分类代码")
    private String classCode;

    @ApiModelProperty(value = "是否可匹配：0不可 1可匹配")
    private Integer isMatchable;
}
