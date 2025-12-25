package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/4/12 19:30
 * @description: 支付结果
 */
@Data
public class PayResultDto implements Serializable {
    private static final long serialVersionUID = -2335470201683137631L;

    @ApiModelProperty(value = "缴费记录ID")
    private String id;

    @ApiModelProperty(value = "支付结果，成功：success、失败：fail")
    private String status;

    @ApiModelProperty(value = "新的版本号")
    private Long version;

    @ApiModelProperty(value = "支付流水号")
    private String payNo;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "pdf地址,第0次打印的时候直接返回")
    private String pdfUrl;
    public PayResultDto() {
    }

    public PayResultDto(String id, String status, Long version, String payNo) {
        this.id = id;
        this.status = status;
        this.version = version;
        this.payNo = payNo;
    }
}
