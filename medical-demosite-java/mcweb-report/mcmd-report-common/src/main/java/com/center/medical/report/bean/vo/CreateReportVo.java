package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建报告返回的数据，保存到md_report_content
 */
@Data
public class CreateReportVo implements Serializable {
    private static final long serialVersionUID = 5346711212992649327L;

    @ApiModelProperty(value = "iReportVo")
    private IReportVo iReportVo;

    @ApiModelProperty(value = "报告图片集合")
    private List<String> reportPicList;

    @ApiModelProperty(value = "报告地址")
    private String reportPath;

    @ApiModelProperty(value = "前缀")
    private String prefix;
}
