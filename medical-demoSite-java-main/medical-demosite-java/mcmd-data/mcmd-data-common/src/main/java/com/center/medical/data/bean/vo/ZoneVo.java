package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 所属地区信息
 */
@Data
public class ZoneVo implements Serializable {
    private static final long serialVersionUID = -6188743165914178786L;

    @ApiModelProperty(value = "区域代码")
    private String zoneCode;

    @ApiModelProperty(value = "区域名称")
    private String zoneName;

    @ApiModelProperty(value = "青岛行政区代码")
    private String qingdaoCode;
}
