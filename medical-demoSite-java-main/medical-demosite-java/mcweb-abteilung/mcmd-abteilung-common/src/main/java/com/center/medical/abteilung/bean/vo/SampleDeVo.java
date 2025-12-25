package com.center.medical.abteilung.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验样本-样本录入分页数据
 */
@Data
public class SampleDeVo implements Serializable {

    private static final long serialVersionUID = 8438035929699377853L;

    @Excel(name = "id")
    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createId;

    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建日期 ")
    private String createdate;
}
