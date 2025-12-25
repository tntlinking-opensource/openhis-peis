package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成体检号的参数
 */
@Data
public class GenerateCodeParam implements Serializable {
    private static final long serialVersionUID = 2358261077924470580L;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "套餐id")
    private String tcId;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "外部订单流水号")
    private String bizPayNo;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "id")
    private String settlementId;

    public GenerateCodeParam(String phone, String tcId, String fzxId) {
        this.phone = phone;
        this.tcId = tcId;
        this.fzxId = fzxId;
    }

    public GenerateCodeParam() {
    }
}
