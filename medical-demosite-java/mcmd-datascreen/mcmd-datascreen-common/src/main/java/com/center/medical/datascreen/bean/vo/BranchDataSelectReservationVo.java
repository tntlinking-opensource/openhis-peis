package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 销售部预约
 * @author xhp
 * @since 2023-06-13 9:04
 */
@Data
public class BranchDataSelectReservationVo {
    @ApiModelProperty(value = "团检(位)")
    private int group;
    @ApiModelProperty(value = "VIP(位)")
    private int vip;
    @ApiModelProperty(value = "贵宾(位)")
    private int guest;
}
