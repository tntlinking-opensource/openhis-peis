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
 * 字典类型(BaseDictionaryClass)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:29
 */
@Data
@TableName("BASE_DICTIONARY_CLASS")
@ApiModel(value = "BaseDictionaryClass", description = "字典类型实体类")
public class BaseDictionaryClass extends Model<BaseDictionaryClass> implements Serializable {
    private static final long serialVersionUID = -64248095681497562L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String className;

    @ApiModelProperty(value = "分类代码")
    private String classCode;

    @ApiModelProperty(value = "是否可匹配  1可匹配")
    private Integer isMatchable;
}
