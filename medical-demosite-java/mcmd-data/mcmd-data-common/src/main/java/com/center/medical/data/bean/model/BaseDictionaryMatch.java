package com.center.medical.data.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)表实体类
 *
 * @author ay
 * @since 2022-11-18 18:16:14
 */
@Data
@TableName("base_dictionary_match")
@ApiModel(value = "BaseDictionaryMatch", description = "体检系统-职业卫生管理平台 字典匹配实体类")
public class BaseDictionaryMatch extends Model<BaseDictionaryMatch> implements Serializable {
    private static final long serialVersionUID = 208686089938249327L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "配对id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "字典id")
    private String dictionaryId;

    @ApiModelProperty(value = "体检系统id")
    private String medicalId;

    @TableField(exist = false)
    @ApiModelProperty(value = "现在的id")
    private String nowId;

    @TableField(exist = false)
    @ApiModelProperty(value = "分类代码")
    private String classCode;
}
