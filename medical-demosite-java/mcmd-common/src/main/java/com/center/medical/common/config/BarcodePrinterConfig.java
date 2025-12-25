package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 条码打印机设置
 */
@Data
public class BarcodePrinterConfig implements Serializable {
    private static final long serialVersionUID = 2424463962775100008L;


    @ApiModelProperty(value = "分中心ID")
    private String cid;

    @ApiModelProperty(value = "内容")
    private List<String> value;

    @ApiModelProperty(value = "前台条码数量")
    private Integer receptionNum;

    @ApiModelProperty(value = "自助登记机条码数量")
    private Integer machineNum;
}
