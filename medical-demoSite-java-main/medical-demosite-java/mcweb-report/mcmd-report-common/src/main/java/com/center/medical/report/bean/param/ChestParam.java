package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class ChestParam implements Serializable {

    private static final long serialVersionUID = 62104284658604317L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "柜子号")
    private String gzh;

}
