package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检预约可用人数 参数
 */
@Data
public class PingAnNumsParam implements Serializable {
    private static final long serialVersionUID = 6520805015155265871L;

    @ApiModelProperty(value = "yyyyMMdd 必须")
    private String startDate;

    @ApiModelProperty(value = "yyyyMMdd 必须")
    private String endDate;

    @ApiModelProperty(value = "机构门店ID 必须")
    private String hospitalSubId;

    @ApiModelProperty(value = "加项 id(多个 id 英文逗号分割),非必须")
    private String addItems;

    @ApiModelProperty(value = "appId")
    private String appId;

}
