package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 设置进报告参数
 */
@Data
public class SetInReportParam implements Serializable {
    private static final long serialVersionUID = -31549472113172110L;

    @NotEmpty(message = "ids不能为空!")
    @ApiModelProperty("ids")
    private List<String> ids;

    @NotNull(message = "inReport不能为空!")
    @ApiModelProperty(value = "是否进报告：0.否 1.是(PACS  彩超控制个检报告和科室报告)")
    private Integer inReport;
}
