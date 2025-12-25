package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据体检号获取所有与模板jyk_1.docx有关的数据
 */
@Data
public class AllInspectDto implements Serializable {
    private static final long serialVersionUID = 7131340454131351334L;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "检查项目打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty("单位")
    private String units;

    @ApiModelProperty(value = "处理状态")
    private String status;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "检查人")
    private String inspectName;

    @ApiModelProperty(value = "审核时间")
    private String auditDate;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "序号")
    private Integer orderIndex;

    @ApiModelProperty(value = "iid")
    private String iid;

    @ApiModelProperty(value = "检查项目ID")
    private String idExamitem;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "收样时间（虹桥lis）")
    private String receiveDate;

    @ApiModelProperty(value = "标本类型ID")
    private String idLabtype;

    @ApiModelProperty(value = "检查项目体征: 描述")
    private String examitemvaluestext;

    @ApiModelProperty(value = "收费项目代码二(艾迪康代码)")
    private String examitemCode3;

    @ApiModelProperty(value = "外送机构ID")
    private String idCooporg;

}
