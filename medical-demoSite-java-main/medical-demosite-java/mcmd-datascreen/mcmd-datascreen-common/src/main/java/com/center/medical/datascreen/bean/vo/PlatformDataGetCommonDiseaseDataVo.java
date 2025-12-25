package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 常见病占比
 * @author xhp
 * @since 2023-06-05 11:13
 */
@Data
public class PlatformDataGetCommonDiseaseDataVo {
    @ApiModelProperty(value = "常见病名称")
    private String name;
    @ApiModelProperty(value = "正常人数")
    private int normal;
    @ApiModelProperty(value = "异常人数")
    private int abnormal;
}
