package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-05-17 16:32
 * @description: 撞单处理提交参数
 */
@Data
public class OrderConflictDeallParam implements Serializable {
    private static final long serialVersionUID = 4030552478961651983L;

    @ApiModelProperty(value = "撞单记录ID")
    private String id;

    @ApiModelProperty(value = "是否撞单：0.否 1.是")
    private Integer dealResult;

    @ApiModelProperty(value = "订单ID")
    private String ddId;

    @ApiModelProperty(value = "备注")
    private String remark;
}
