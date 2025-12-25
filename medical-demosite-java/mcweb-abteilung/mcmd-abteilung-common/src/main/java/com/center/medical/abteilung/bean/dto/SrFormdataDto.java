package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 外送登记，保存更新数据
 */
@Data
public class SrFormdataDto implements Serializable {
    private static final long serialVersionUID = -4060412648058220L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "承送人")
    private String sendPeople;

    @ApiModelProperty(value = "承送时间")
    private Date sendDate;

    @ApiModelProperty(value = "外送备注")
    private String note;

}
