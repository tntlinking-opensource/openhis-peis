package com.center.medical.data.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 标本种类(SpecimenType)表实体类
 *
 * @author ay
 * @since 2023-11-07 15:49:17
 */
@Data
@TableName("md_specimen_type")
@ApiModel(value = "SpecimenType", description = "标本种类实体类")
public class SpecimenType extends Model<SpecimenType> implements Serializable {
    private static final long serialVersionUID = 717981670491468834L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "代码")
    private String code;


    @ApiModelProperty(value = "样本名称")
    private String specimenName;


    @ApiModelProperty(value = "国标代码")
    private String nationalStandardCode;


    @ApiModelProperty(value = "his代码")
    private String hisCode;


    @ApiModelProperty(value = "状态：-1.删除 0.下线 1.正常")
    private Integer status;


    @ApiModelProperty(value = "拼音码")
    private String pinyinCode;


    @ApiModelProperty(value = "五笔码")
    private String fiveStrokeCode;


    @ApiModelProperty(value = "自定义码")
    private String customCode;


    @ApiModelProperty(value = "类别")
    private String category;


    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

}
