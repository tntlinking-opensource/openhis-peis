package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出(疫苗名单)
 */
@Data
public class ExportVaccineNameVo implements Serializable {
    private static final long serialVersionUID = -4142686515556369232L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "顾客姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实付价格")
    private Double factprice;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name="登记日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name="收费日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @Excel(name = "疫苗名字")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;
}
