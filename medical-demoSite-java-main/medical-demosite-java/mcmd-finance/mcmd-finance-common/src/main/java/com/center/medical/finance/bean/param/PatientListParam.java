package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平安对账单 获取体检者数据 参数
 */
@Data
public class PatientListParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3304932893525668056L;

    @ApiModelProperty(value = "已登记")
    private Integer containUnchecked;

    @ApiModelProperty(value = "订单ID")
    private String idOrder;

    @ApiModelProperty(value = "分中心ID")
    private String center;

}
