package com.center.medical.data.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型(BaseDictionaryClass)表实体类
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
@Data
@TableName("base_dictionary_class")
@ApiModel(value = "BaseDictionaryClass", description = "字典类型实体类")
public class BaseDictionaryClass extends Model<BaseDictionaryClass> implements Serializable {
    private static final long serialVersionUID = 602972912741379511L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String className;

    @ApiModelProperty(value = "分类代码")
    private String classCode;

    @ApiModelProperty(value = "是否可匹配：0不可 1可匹配")
    private Integer isMatchable;
}
