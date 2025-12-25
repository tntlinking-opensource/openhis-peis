package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序获取文章列表返回数据
 */
@Data
public class GetArticleListVo implements Serializable {
    private static final long serialVersionUID = -1299130171509533031L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "类型 0文章 1外链")
    private Integer type;

    @ApiModelProperty(value = "分类id")
    private Integer classify;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "顺序")
    private Integer seq;

    @ApiModelProperty(value = "创建时间")
    private String createdate;

}
