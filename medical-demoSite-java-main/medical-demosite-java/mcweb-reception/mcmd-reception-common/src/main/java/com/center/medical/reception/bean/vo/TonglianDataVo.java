package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日自助机通联明细返回数据
 */
@Data
public class TonglianDataVo implements Serializable {
    private static final long serialVersionUID = 4843631893574057352L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "客户单位团体号")
    private String intId;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "体检者")
    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @Excel(name = "支付平台订单号")
    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @Excel(name = "实收",cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @Excel(name = "收费人")
    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @Excel(name="收费日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @Excel(name = "体检类型" ,readConverterExp = "0=健康,1=职业,2=综合,3=复查")
    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

}
