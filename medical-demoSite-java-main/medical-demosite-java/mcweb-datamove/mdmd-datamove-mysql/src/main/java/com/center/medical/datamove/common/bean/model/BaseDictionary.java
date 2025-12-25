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
 * 字典(BaseDictionary)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:55
 */
@Data
@TableName("base_dictionary")
@ApiModel(value = "BaseDictionary", description = "字典实体类")
public class BaseDictionary extends Model<BaseDictionary> implements Serializable {
    private static final long serialVersionUID = -70027628090968604L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "字典类型编码")
    private String dictionaryType;

    @ApiModelProperty(value = "编码")
    private String dictionaryCode;

    @ApiModelProperty(value = "名称")
    private String dictionaryName;

    @ApiModelProperty(value = "是否删除：0未删除  1已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "体检系统代码（废弃）")
    private String medicalCode;

    @ApiModelProperty(value = "山东接口代码（废弃）")
    private String shandongCode;

    @ApiModelProperty(value = "青岛接口代码（废弃）")
    private String qingdaoCode;

    @ApiModelProperty(value = "长沙接口代码")
    private String changshaCode;
}
