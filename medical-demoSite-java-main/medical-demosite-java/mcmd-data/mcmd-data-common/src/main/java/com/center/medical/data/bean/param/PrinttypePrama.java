package com.center.medical.data.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-18 8:58
 * @description:
 */
@Data
public class PrinttypePrama extends BaseParam implements Serializable {

    private static final long serialVersionUID = -3684286796837804227L;

    @ApiModelProperty(value = "打印项目分类名称")
    private String printName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

}
