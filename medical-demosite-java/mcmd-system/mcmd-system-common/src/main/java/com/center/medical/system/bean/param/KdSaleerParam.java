package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-05-19 14:55
 * @description: 金蝶销售员查询参数
 */
@Data
public class KdSaleerParam implements Serializable {
    private static final long serialVersionUID = 2119964410849359477L;

    @ApiModelProperty(value = "账户名称")
    private String accountName;
}
