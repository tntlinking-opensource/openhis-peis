package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验样本-样本交接分页数据
 */
@Data
public class SampleConnectVo implements Serializable {

    private static final long serialVersionUID = 783728376767575087L;

    @ApiModelProperty(value = "样本个数")
    private String count;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "交接人名称")
    private String deliveryName;

    @ApiModelProperty(value = "交接时间")
    private Date deliveryTime;

    @ApiModelProperty(value = "姓名id")
    private String deliveryNameId;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别0男1女")
    private String idSex;

    @ApiModelProperty(value = "承接人")
    private String recipient;
}
