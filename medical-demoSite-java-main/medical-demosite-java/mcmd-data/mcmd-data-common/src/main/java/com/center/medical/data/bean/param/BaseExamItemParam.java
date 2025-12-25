package com.center.medical.data.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-17 10:20
 * @description: 检查项目参数
 */
@Data
public class BaseExamItemParam extends BaseParam implements Serializable {


    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "性别：0男性 1女性 2通用")
    private Integer forMale;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer type;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

}
