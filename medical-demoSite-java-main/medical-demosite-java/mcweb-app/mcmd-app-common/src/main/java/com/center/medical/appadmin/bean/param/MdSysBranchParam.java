package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分中心设置分页参数
 */
@Data
public class MdSysBranchParam implements Serializable {
    private static final long serialVersionUID = 497077527789897868L;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    @ApiModelProperty(value = "是否展示：0.否 1.是")
    private Integer isShow;
}
