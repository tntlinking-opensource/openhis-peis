package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取小程序文章参数
 */
@Data
public class GetArticleListParam implements Serializable {
    private static final long serialVersionUID = 4693638492527025402L;

    @ApiParam(value = "每页大小，默认10", required = false, defaultValue = "10")
    private long size = 10;


    @ApiParam(value = "当前页，默认1", required = false, defaultValue = "0")
    private long current = 0;


    @ApiModelProperty(value = "分类")
    private Integer classify;

}
