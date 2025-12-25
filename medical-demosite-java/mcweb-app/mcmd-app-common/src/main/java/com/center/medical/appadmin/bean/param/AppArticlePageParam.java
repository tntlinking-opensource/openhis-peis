package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序文章分页参数
 */
@Data
public class AppArticlePageParam implements Serializable {
    private static final long serialVersionUID = -8091663995704102779L;

    @ApiModelProperty(value = "分类id")
    private String classify;


    @ApiModelProperty(value = "标题")
    private String title;


    @ApiModelProperty(value = "状态 -1下线 ,1上线 ")
    private String status;

}
