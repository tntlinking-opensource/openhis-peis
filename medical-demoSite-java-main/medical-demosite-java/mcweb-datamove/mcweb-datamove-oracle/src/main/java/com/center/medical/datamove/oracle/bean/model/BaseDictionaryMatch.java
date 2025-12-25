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
 * 体检系统-职业卫生管理平台 字典匹配(BaseDictionaryMatch)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:31
 */
@Data
@TableName("BASE_DICTIONARY_MATCH")
@ApiModel(value = "BaseDictionaryMatch", description = "体检系统-职业卫生管理平台 字典匹配实体类")
public class BaseDictionaryMatch extends Model<BaseDictionaryMatch> implements Serializable {
    private static final long serialVersionUID = -91162300436970197L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "字典id")
    private String dictionaryId;

    @ApiModelProperty(value = "体检系统id")
    private String medicalId;
}
