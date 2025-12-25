package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费日报获取数据 返回数据
 */
@Data
public class CQListDataVo implements Serializable {
    private static final long serialVersionUID = -110847057518071456L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "收费金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "团检单位")
    private String orgName;

    @ApiModelProperty(value = "体检卡")
    private String tjk;

    @ApiModelProperty(value = "套餐")
    private String examsuiteName;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检类型id")
    private String idExamtype;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @ApiModelProperty(value = "体检者记时器开始")
    private Date timingstartedat;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

}
