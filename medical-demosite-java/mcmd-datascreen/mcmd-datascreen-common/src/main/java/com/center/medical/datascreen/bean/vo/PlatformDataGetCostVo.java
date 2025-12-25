package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 成本展示
 * @author xhp
 * @since 2023-06-05 8:28
 */
@Data
public class PlatformDataGetCostVo {
    @ApiModelProperty(value = "医生人员数量")
    private int doctor;
    @ApiModelProperty(value = "护理人员数量")
    private int nurse;
}
