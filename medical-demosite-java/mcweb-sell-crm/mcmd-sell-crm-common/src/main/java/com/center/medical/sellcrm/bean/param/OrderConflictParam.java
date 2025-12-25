package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-05-17 8:25
 * @description: 撞单查询参数
 */
@Data
public class OrderConflictParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 3929370955888654573L;

    @ApiModelProperty(value = "订单ID(当前提交的订单)")
    private String ddId;

    @ApiModelProperty(value = "撞单的订单ID集合，多个以英文逗号隔开")
    private String planIds;

    @ApiModelProperty(value = "是否撞单：0.否 1.是")
    private Integer dealResult;

    @ApiModelProperty(value = "状态：0.待处理 1.已处理")
    private Integer status;

}
