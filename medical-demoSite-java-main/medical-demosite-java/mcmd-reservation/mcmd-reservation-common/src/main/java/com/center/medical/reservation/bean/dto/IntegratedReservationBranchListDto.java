package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新老系统集成预约接口-分中心列表返回
 * @author xhp
 * @since 2024-01-04 8:36
 */
@Data
public class IntegratedReservationBranchListDto {
    @ApiModelProperty(value = "机构门店ID")
    private String hospitalSubId;

    @ApiModelProperty(value = "机构门店名称")
    private String hospitalSubName;

}
