package com.center.medical.data.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典(BaseDictionary)表实体类
 *
 * @author ay
 * @since 2022-11-18 18:16:13
 */
@Data
@TableName("base_dictionary")
@ApiModel(value = "BaseDictionary", description = "字典实体类")
public class BaseDictionary extends Model<BaseDictionary> implements Serializable {
    private static final long serialVersionUID = 364416773539639094L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
