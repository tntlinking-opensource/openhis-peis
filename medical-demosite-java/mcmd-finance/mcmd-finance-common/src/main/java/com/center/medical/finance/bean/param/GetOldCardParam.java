package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取老系统卡参数
 */
@Data
public class GetOldCardParam implements Serializable {
    private static final long serialVersionUID = -1877173126695235841L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    public GetOldCardParam(String authCode, String cardNo) {
        this.authCode = authCode;
        this.cardNo = cardNo;
    }

    public GetOldCardParam() {
    }
}
