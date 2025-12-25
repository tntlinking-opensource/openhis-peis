package com.center.medical.report.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业团检样本导出数据
 */
@Data
public class GHExportDataVo implements Serializable {
    private static final long serialVersionUID = -5908761824089318263L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "开始体检")
    @ApiModelProperty(value = "已开始体检：0或null.否 1.是")
    private Integer fExamstarted;

    @Excel(name = "总检完成")
    @ApiModelProperty(value = "总检完成")
    private Double fFinalexamed;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "团体部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;
}
