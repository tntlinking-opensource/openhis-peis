package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewBatchParam implements Serializable {
    private static final long serialVersionUID = 6672862376617480433L;


    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "团体id")
    private String orgId;
}
