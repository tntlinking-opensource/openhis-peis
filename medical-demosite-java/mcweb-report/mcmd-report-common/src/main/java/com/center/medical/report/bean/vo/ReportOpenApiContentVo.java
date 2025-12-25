package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 报告外部接口根据体检号获取报告数据
 * @author xhp
 * @since 2023-10-26 10:12
 */
@Data
public class ReportOpenApiContentVo {
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "体检类型,0.健康体检 1.职业体检")
    private String idExamtype;
}
