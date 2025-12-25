package com.center.medical.common.core.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 判断当前登录者是否为领导。领导加载本分中心下所有的销售人员的数据；不是领导加载自己的数据
 */
@Data
public class XsryDataVo implements Serializable {
    private static final long serialVersionUID = -7867449929238779442L;

    @ApiModelProperty(value = "userNo拼接在一起")
    private String userNos;

    @ApiModelProperty(value = "userNames拼接在一起")
    private String userNames;

}
