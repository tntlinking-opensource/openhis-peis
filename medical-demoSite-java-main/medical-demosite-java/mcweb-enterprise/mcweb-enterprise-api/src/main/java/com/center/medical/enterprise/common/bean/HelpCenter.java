package com.center.medical.enterprise.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 帮助中心返回数据
 */
@Data
public class HelpCenter implements Serializable {
    private static final long serialVersionUID = -1222439227936527694L;

    @ApiModelProperty(value = "对象集合")
    private List<HelpCenterDto> list;

}
