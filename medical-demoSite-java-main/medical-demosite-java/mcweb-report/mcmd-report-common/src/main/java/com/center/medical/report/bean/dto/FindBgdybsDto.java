package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 只显示隐私项目 返回数据
 */
@Data
public class FindBgdybsDto implements Serializable {
    private static final long serialVersionUID = -5036474727102005974L;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "检查人")
    private String inspectName;

    @ApiModelProperty(value = "审核日期")
    private Date auditdate;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "序号")
    private Integer orderIndex;

    @ApiModelProperty(value = "iid")
    private String iid;

}
