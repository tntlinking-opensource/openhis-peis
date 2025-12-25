package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-16 8:17
 * @description: 收费信息
 */
@Data
public class PeispatientchargeDto implements Serializable {
    private static final long serialVersionUID = -144405471135592413L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号(ID)")
    private String patientcode;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "是否已收费：0.否 1.是")
    private Integer fFeecharged;

    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "是否统收：0:不是统收1：统收")
    private Integer isTotalcharge;

    @ApiModelProperty(value = "tong")
    private Integer tong;

    @ApiModelProperty(value = "顺序")
    private Integer numIndex;

    @ApiModelProperty(value = "收费项目类型：removed：删除、modified：变更（弃检、退项、迟检、补检、拒检）、added：加项")
    private String state;

//    public PeispatientchargeDto(String patientcode, String idPayway, String payway, Double moneyamount, Double moneyamountpaid, Integer fFeecharged, Date feechargetime, String cardno, Integer isTotalcharge, Integer tong, Integer numIndex, String state) {
//        this.patientcode = patientcode;
//        this.idPayway = idPayway;
//        this.payway = payway;
//        this.moneyamount = moneyamount;
//        this.moneyamountpaid = moneyamountpaid;
//        this.fFeecharged = fFeecharged;
//        this.feechargetime = feechargetime;
//        this.cardno = cardno;
//        this.isTotalcharge = isTotalcharge;
//        this.tong = tong;
//        this.numIndex = numIndex;
//        this.state = state;
//    }
}
