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
 * 字典(BaseDictionary)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:28
 */
@Data
@TableName("BASE_DICTIONARY")
@ApiModel(value = "BaseDictionary", description = "字典实体类")
public class BaseDictionary extends Model<BaseDictionary> implements Serializable {
    private static final long serialVersionUID = 614480025350592769L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "字典类型编码")
    private String dictionaryType;

    @ApiModelProperty(value = "编码")
    private String dictionaryCode;

    @ApiModelProperty(value = "名称")
    private String dictionaryName;

    @ApiModelProperty(value = "0未删除  1已删除")
    private String isDelete;

    @ApiModelProperty(value = "体检系统代码（废弃）")
    private String medicalCode;

    @ApiModelProperty(value = "山东接口代码（废弃）")
    private String shandongCode;

    @ApiModelProperty(value = "青岛接口代码（废弃）")
    private String qingdaoCode;

    @ApiModelProperty(value = "长沙接口代码")
    private String changshaCode;
}
