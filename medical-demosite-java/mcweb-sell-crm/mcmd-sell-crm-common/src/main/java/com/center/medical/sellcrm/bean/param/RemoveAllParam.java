package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导入名单删除参数
 */
@Data
public class RemoveAllParam implements Serializable {
    private static final long serialVersionUID = -3221983111186021950L;

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "体检者id,可以传多个")
    private List<String> ids;
}
