package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序文章添加或修改参数
 */
@Data
public class AppArticleParam implements Serializable {
    private static final long serialVersionUID = -8091663995704102779L;


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


    @ApiModelProperty(value = "顺序")
    private Integer seq;



}
