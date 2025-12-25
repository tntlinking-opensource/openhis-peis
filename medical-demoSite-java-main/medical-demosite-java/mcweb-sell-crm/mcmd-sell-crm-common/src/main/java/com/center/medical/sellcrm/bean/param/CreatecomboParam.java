package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-15 10:51
 * @description: 最小套餐查询参数
 */
@Data
public class CreatecomboParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2938043055394810522L;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer isBan;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

}
