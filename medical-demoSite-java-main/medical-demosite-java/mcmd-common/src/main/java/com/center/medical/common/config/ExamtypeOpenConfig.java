package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记体检类型开启设置
 */
@Data
public class ExamtypeOpenConfig implements Serializable {
    private static final long serialVersionUID = 3045193062157303771L;

    @ApiModelProperty(value = "健康")
    private Boolean health;

    @ApiModelProperty(value = "职业")
    private Boolean occupation;

    @ApiModelProperty(value = "综合")
    private Boolean comprehensive;

    @ApiModelProperty(value = "复查")
    private Boolean review;

    @ApiModelProperty(value = "是否查询老系统")
    private Boolean searchOldSystems;
}
