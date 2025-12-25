package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回符合条件的收费项目数据参数
 */
@Data
public class GetShowParam implements Serializable {
    private static final long serialVersionUID = -2765268976702092067L;

    @ApiModelProperty(value = "收费项目输入码")
    private String key;

    @ApiModelProperty(value = "性别")
    private Integer syxb;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "分中心id")
    private List<String> fzxid;
}
