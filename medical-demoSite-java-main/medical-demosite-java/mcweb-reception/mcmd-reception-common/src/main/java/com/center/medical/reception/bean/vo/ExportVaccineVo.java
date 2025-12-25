package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出(疫苗费用)
 */
@Data
public class ExportVaccineVo implements Serializable {
    private static final long serialVersionUID = -3813470627764693389L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "体检者")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @Excel(name = "收费人")
    @ApiModelProperty(value = "收费员姓名")
    private String name;

    @Excel(name="收费日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    public ExportVaccineVo() {
    }

    public ExportVaccineVo(String paywayName, Double moneyamountpaid) {
        this.paywayName = paywayName;
        this.moneyamountpaid = moneyamountpaid;
    }
}
