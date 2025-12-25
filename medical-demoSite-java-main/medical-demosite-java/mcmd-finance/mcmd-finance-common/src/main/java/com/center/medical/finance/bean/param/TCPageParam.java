package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 团体结算 分页 参数
 */
@Data
public class TCPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 6665616030069569257L;


    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "客户单位名称id")
    private String khdwmcid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

}
