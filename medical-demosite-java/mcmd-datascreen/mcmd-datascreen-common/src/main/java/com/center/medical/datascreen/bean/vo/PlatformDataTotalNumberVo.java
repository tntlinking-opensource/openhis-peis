package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 总会员
 * @author xhp
 * @since 2024-04-02 11:03
 */
@Data
public class PlatformDataTotalNumberVo {
    @ApiModelProperty(value = "总会员")
    private int number;
}
