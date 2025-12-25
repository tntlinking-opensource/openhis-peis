package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存订单信息
 */
@Data
public class COFormDataDto implements Serializable {

    private static final long serialVersionUID = 5981418158941541451L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "上传文件")
    private String uploadFile;

}
