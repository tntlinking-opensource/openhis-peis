package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 银行交易流水更新 参数
 */
@Data
public class KingdeeRemittanceParam implements Serializable {
    private static final long serialVersionUID = -7542655617262711209L;


    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "分中心列表")
    private List<String> branchList;
}
