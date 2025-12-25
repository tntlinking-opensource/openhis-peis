package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序类型添加或修改参数
 */
@Data
public class AppArticleTypeParam implements Serializable {
    private static final long serialVersionUID = -3003562906658243392L;


    @ApiModelProperty(value = "主键")
    private String id;


    @ApiModelProperty(value = "名称")
    private String name;


    @ApiModelProperty(value = "备注")
    private String note;


    @ApiModelProperty(value = "序号")
    private Integer seq;
}
