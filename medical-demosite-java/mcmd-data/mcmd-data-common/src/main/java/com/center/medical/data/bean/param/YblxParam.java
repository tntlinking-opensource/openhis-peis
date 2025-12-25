package com.center.medical.data.bean.param;

import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-29 11:29
 * @description:
 */
@Data
public class YblxParam implements Serializable {
    private static final long serialVersionUID = -1618043562532735945L;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }
}
