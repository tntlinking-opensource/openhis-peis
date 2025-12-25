package com.center.medical.report.bean.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 隐私报告 获取隐私项目
 */
@Data
public class PrYsxmDto implements Serializable {
    private static final long serialVersionUID = -545830877707073018L;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty("单位")
    private String units;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "检查人")
    private String inspectName;

    @ApiModelProperty(value = "审核时间")
    private String auditDate;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "体征词显示顺序")
    private Integer orderindex;

    @ApiModelProperty(value = "iid")
    private String iid;

    @ApiModelProperty(value = "签名图片")
    private String signPic;

}
