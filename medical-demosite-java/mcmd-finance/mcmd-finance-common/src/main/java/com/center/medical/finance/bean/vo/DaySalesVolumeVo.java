package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当日线下业绩
 */
@Data
public class DaySalesVolumeVo implements Serializable {
    private static final long serialVersionUID = 5262556473504467466L;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "总金额")
    private String amount;


    public DaySalesVolumeVo() {
    }

    public DaySalesVolumeVo(String fzx, String amount) {
        this.fzx = fzx;
        this.amount = amount;
    }
}
