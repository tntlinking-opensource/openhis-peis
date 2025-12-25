package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序文章表(AppArticle)表实体类
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
@Data
@TableName("md_app_article")
@ApiModel(value = "AppArticle", description = "小程序文章表实体类")
public class AppArticle extends Model<AppArticle> implements Serializable {
    private static final long serialVersionUID = 408864910179244355L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "类型 0文章 1外链")
    private Integer type;


    @ApiModelProperty(value = "分类id")
    private Integer classify;


    @ApiModelProperty(value = "缩略图")
    private String thumbnail;


    @ApiModelProperty(value = "标题")
    private String title;


    @ApiModelProperty(value = "内容")
    private String content;


    @ApiModelProperty(value = "备注")
    private String bz;


    @ApiModelProperty(value = "状态 0下线 ,1上线")
    private Integer status;


    @ApiModelProperty(value = "顺序")
    private Integer seq;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;


    @TableField(exist = false)
    @ApiModelProperty(value = "分类名称")
    private String classifyName;

}
