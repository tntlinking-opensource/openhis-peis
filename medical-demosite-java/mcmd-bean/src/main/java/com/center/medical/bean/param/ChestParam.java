package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 报告柜子管理分页参数
 */
@Data
public class ChestParam implements Serializable {

    private static final long serialVersionUID = 5421260704225582298L;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "柜子号")
    private String gzh;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;
}
