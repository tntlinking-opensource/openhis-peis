package com.center.medical.pacslis.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 获取pacs结果
 * @author xhp
 * @since 2023-06-25 8:06
 */
@Data
public class PacsReceiveParam {
    @ApiModelProperty(value = "体检号")
    @NotEmpty
    private String patientcode;
}
