package com.center.medical.data.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-16 15:28
 * @description:
 */
@Data
public class HarmParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3748808506364248205L;

    @ApiModelProperty(value = "危害因素代码")
    private String harmCode;

    @ApiModelProperty(value = "危害因素名字")
    private String harmName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)){
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "级别：1.级父级  2.级子级")
    private Integer lv;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "类别id")
    private String harmClass;

}
