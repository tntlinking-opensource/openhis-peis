package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取经济类型返回数据
 */
@Data
public class EconomyCodeVo implements Serializable {
    private static final long serialVersionUID = -2150245494597790236L;

    @ApiModelProperty(value = "编码")
    private String economyCode;

    @ApiModelProperty(value = "名称")
    private String economyName;

}
