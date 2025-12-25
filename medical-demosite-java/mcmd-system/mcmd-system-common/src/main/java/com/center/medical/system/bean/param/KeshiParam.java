package com.center.medical.system.bean.param;

import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-11-29 11:56
 * @description: 科室查询参数
 */
@Data
public class KeshiParam {
    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }
}
