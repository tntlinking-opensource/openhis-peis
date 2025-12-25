package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记账管理-银行汇款结算 分页 参数
 */
@Data
public class BSPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8078370969637830212L;

    @ApiModelProperty(value = "对方户名")
    private String remitter;

    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "是否审核")
    private Integer isSh;

    @ApiModelProperty(value = "是否结算")
    private Integer isJs;

}
