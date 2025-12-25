package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取所属区域
 */
@Data
public class UnitAreaVo implements Serializable {
    private static final long serialVersionUID = 4801489850894465407L;

    @ApiModelProperty(value = "区域代码")
    private String zoneCode;

    @ApiModelProperty(value = "区域名称")
    private String zoneName;
}
