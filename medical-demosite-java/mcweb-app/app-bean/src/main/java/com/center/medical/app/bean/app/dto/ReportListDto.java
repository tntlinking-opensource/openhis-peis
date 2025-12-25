package com.center.medical.app.bean.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告列表返回数据
 */
@Data
public class ReportListDto implements Serializable {
    private static final long serialVersionUID = 4132947785314670007L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 7.Pacs报告 8.职业结果告知书 9.检验报告(除检验科的)")
    private String reportType;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "报告类型名称")
    private String reportTypeName;
}
