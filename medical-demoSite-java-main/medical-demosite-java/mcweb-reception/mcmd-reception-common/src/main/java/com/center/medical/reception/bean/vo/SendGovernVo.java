package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送结果上传分页数据
 */
@Data
public class SendGovernVo implements Serializable {
    private static final long serialVersionUID = -6202276281276364658L;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "乘送人")
    private String sendPeople;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;

    @ApiModelProperty(value = "外送时间")
    private String sendDate;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "返回时间")
    private String backDate;

    @ApiModelProperty(value = "返回人")
    private String backPeople;
}
