package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询上传金蝶云数据
 */
@Data
public class KingdeeUploadDataDto implements Serializable {
    private static final long serialVersionUID = 3465990545620468495L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "体检中心名称")
    private String centerorgname;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private String fUsecodehiden;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "人员数量")
    private double countNum;

    @ApiModelProperty(value = "优惠金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "折扣率")
    private double zkl;

    @ApiModelProperty(value = "销售经理")
    private String doctorapply;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "是否记账数据 0否1是")
    private Integer isjz;

}
