package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-12-08 9:40
 * @description: 总检结论词查询参数
 */
@Data
public class TotalVerdictParam implements Serializable {
    private static final long serialVersionUID = -7370410442557585555L;

    @NotBlank(message = "体检号不能为空！")
    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "结论词类型：0.健康 1.职业")
    private Integer dh;
}
