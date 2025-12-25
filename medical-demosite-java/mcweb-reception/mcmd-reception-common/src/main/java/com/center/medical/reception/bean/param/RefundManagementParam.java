package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 退款管理参数
 */
@Data
public class RefundManagementParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5032201093706084906L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "0收入，1退费")
    private Integer fIsreturn;
}
