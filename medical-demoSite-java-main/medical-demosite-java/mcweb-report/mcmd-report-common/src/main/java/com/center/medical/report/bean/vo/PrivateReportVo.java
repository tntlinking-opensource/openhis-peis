package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 生成隐私报告 返回数据
 */
@Data
public class PrivateReportVo implements Serializable {
    private static final long serialVersionUID = -7143656635996787844L;

    @ApiModelProperty(value = "头部")
    private Map<String,Object> head;

    @ApiModelProperty(value = "主体数据")
    private List<Map<String,Object>> examdata;

    @ApiModelProperty(value = "模板名字,private_report.docx 或 private_report_noyg.docx")
    private String modelName;
}
