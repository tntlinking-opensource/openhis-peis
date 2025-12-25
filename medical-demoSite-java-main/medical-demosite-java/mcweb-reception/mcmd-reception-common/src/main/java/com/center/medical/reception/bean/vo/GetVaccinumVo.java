package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日疫苗收费统计 分页返回数据
 */
@Data
public class GetVaccinumVo implements Serializable {
    private static final long serialVersionUID = 4685486553091143370L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "登记用户")
    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "收费方式")
    private String paywayName;

    @Excel(name = "支付平台订单号")
    @ApiModelProperty(value = "支付单号或卡号")
    private String cardno;

    @Excel(name = "实收")
    @ApiModelProperty(value = "实收金额")
    private String moneyamountpaid;

    @Excel(name = "收费人")
    @ApiModelProperty(value = "收费人")
    private String userName;

    @Excel(name = "收费项目名称")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorreg;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
