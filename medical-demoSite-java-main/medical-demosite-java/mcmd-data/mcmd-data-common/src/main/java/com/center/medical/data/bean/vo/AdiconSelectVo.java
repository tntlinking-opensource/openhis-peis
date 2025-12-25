package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通过输入码查询艾迪康项目代码表
 */
@Data
public class AdiconSelectVo implements Serializable {
    private static final long serialVersionUID = -7435271355379982352L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String text;
}
