package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 加载所有最小套餐按照分中心参数
 */
@Data
public class ZxtcsDataParam implements Serializable {
    private static final long serialVersionUID = 1787658821658764012L;

    @ApiModelProperty(value = "key")
    private String key;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlx;

    @ApiModelProperty(value = "接害因素集合")
    private List<String> jhys;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心")
    private String fzxid;

    @ApiModelProperty(value = "折后价格")
    private String zhjg;
}
