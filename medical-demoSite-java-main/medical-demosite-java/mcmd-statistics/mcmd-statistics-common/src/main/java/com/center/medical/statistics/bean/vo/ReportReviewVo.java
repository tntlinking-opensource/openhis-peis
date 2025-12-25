package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 报告审核工作量分页返回数据
 */
@Data
public class ReportReviewVo implements Serializable {
    private static final long serialVersionUID = 6526489266252124952L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "终审人")
    @ApiModelProperty(value = "终审人")
    private String lastName;

    @ApiModelProperty(value = "终审时间")
    private String lastTime;

    @Excel(name = "工作量")
    @ApiModelProperty(value = "合计")
    private String total;

    @Excel(name = "个人")
    @ApiModelProperty(value = "合计个人")
    private String totalPersonal;

    @Excel(name = "团体")
    @ApiModelProperty(value = "合计分组")
    private String totalGroup;

}
