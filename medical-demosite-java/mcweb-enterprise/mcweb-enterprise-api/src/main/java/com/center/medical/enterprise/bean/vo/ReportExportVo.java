package com.center.medical.enterprise.bean.vo;

import com.center.medical.enterprise.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 报告导出 返回数据
 */
@Data
public class ReportExportVo implements Serializable {
    private static final long serialVersionUID = 8369143738347354244L;

    @Excel(name = "体检号",sort = 1, width = 25)
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名",sort = 2, width = 25)
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "身份证号",sort = 3, width = 25)
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "检查项目打印名称")
    private String itemName;

    @Excel(name = "结果")
    @ApiModelProperty(value = "结果")
    private String result;


}
