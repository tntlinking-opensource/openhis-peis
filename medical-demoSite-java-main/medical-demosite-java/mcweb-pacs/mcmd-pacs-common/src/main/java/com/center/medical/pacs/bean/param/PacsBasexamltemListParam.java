package com.center.medical.pacs.bean.param;

import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xhp
 * @since 2023-03-29 8:06
 */
@Data
public class PacsBasexamltemListParam implements Serializable {

    private static final long serialVersionUID = 8842682061112970421L;
    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "性别：0.代表男 1.代表女 2.通用")
    private Integer forMale;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "类型：0.健康检查类型 1.职业检查类型 2.综合")
    private Integer type;

    @ApiModelProperty(value = "科室编号")
    private String divisionId;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }
}
