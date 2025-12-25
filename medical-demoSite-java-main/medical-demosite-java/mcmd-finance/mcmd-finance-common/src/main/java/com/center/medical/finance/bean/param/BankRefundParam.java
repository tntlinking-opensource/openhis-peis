package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 银行汇款结算 导出统收参数
 */
@Data
public class BankRefundParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2662718071543047478L;

    @ApiModelProperty(value = "TongShou 团检-统收,ChongKa 充卡-银行存款 ,DaiShou 代收体检费-银行存款，GeJian 个检-记账")
    private String task;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "由task转化的reserWay")
    private String reserWay;

}
