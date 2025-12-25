package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取结算名字 返回数据
 */
@Data
public class NameNumberVo implements Serializable {
    private static final long serialVersionUID = 8911238934192148982L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "id")
    private String id;
}
