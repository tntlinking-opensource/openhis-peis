package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新版取消预约 参数
 */
@Data
public class NewCancelParam implements Serializable {
    private static final long serialVersionUID = 4579616152085724272L;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;


    @ApiModelProperty(value = "用户id(不用传)")
    private String userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

}
