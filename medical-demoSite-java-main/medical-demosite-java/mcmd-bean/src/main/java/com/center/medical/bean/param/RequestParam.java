package com.center.medical.bean.param;

import com.center.medical.common.constant.RequestFlag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/3/27 09:45
 * @description: 消费者消费消息的参数
 */
@Data
public class RequestParam implements Serializable {
    private static final long serialVersionUID = -7997708374060394322L;

    /**
     * 线上线下：0.线下 1.线上
     */
    @ApiModelProperty(value = "线上线下：0.线下 1.线上")
    private Integer online;

    /**
     * 分中心ID，多个以英文逗号隔开
     */
    @ApiModelProperty(value = "分中心ID，多个以英文逗号隔开")
    private String branchId;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 业务标识
     */
    @ApiModelProperty(value = "业务标识")
    private String bsFlag;

    /**
     * 业务标识序号
     * @see RequestFlag
     */
    @ApiModelProperty(value = "业务标识序号")
    private Integer bsFlagNum;

    /**
     * 业务授权码
     */
    @ApiModelProperty(value = "业务授权码")
    private String authCode;
}
