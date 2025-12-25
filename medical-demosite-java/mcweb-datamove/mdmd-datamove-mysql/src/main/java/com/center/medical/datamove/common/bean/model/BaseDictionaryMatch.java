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
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
@Data
@TableName("base_dictionary_match")
@ApiModel(value = "BaseDictionaryMatch", description = "体检系统-职业卫生管理平台 字典匹配实体类")
public class BaseDictionaryMatch extends Model<BaseDictionaryMatch> implements Serializable {
    private static final long serialVersionUID = -51326120312206611L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "配对id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "字典id")
    private String dictionaryId;

    @ApiModelProperty(value = "体检系统id")
    private String medicalId;
}
