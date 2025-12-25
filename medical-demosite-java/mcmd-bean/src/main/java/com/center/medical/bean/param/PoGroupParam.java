package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-17 16:33
 * @description: 获取分中心团体列表查询参数
 */
@Data
public class PoGroupParam implements Serializable {
    private static final long serialVersionUID = 5843446272927571900L;
    
    @ApiModelProperty(value = "团体ID")
    private String khdwmcid;

    @ApiModelProperty(value = "销售经理")
    private String khdwlxr;

    @ApiModelProperty(value = "销售联系方式")
    private String phone;

    @ApiModelProperty(value = "套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
