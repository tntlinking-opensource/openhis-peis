package com.center.medical.abteilung.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送登记数据
 */
@Data
public class OutsideVo implements Serializable {
    private static final long serialVersionUID = 4766729380025983192L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String xm;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String xb;

    @Excel(name = "承送人")
    @ApiModelProperty(value = "承送人")
    private String sendPeople;

    @Excel(name = "外送机构")
    @ApiModelProperty(value = "外送机构")
    private String wsjg;

    @Excel(name = "外送时间")
    @ApiModelProperty(value = "外送时间")
    private String sendDate;

    @ApiModelProperty(value = "返回时间")
    private String backDate;

    @ApiModelProperty(value = "返回人")
    private String backPeople;
}
