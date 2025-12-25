package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改团检预约码有效期或状态
 */
@Data
public class ModifyGroupCodeParam implements Serializable {
    private static final long serialVersionUID = 416556900006830343L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "有效期,7,14,30,999")
    private Integer expirationDate;

    @ApiModelProperty(value = "状态 0生效1过期")
    private Integer status;

}
