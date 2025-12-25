package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出Excel(记账报表) 返回数据
 */
@Data
public class ExportEveryDayVo implements Serializable {
    private static final long serialVersionUID = 3836534095170104115L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "体检者")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "记账金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "记账金额")
    private Double jzje;

    @Excel(name = "记账人")
    @ApiModelProperty(value = "记账人")
    private String jzdwr;

    @Excel(name = "记账单位")
    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @Excel(name = "审批人")
    @ApiModelProperty(value = "审批人")
    private String spr;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @Excel(name = "记账已结", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String username;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @Excel(name="记账日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;


}
