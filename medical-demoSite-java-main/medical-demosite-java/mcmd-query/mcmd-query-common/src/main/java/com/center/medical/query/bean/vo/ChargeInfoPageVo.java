package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费信息查询 分页 返回数据
 */
@Data
public class ChargeInfoPageVo implements Serializable {
    private static final long serialVersionUID = -4556951438159170937L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检码")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "收费日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式")
    private String idPayway;

    @Excel(name = "收费员")
    @ApiModelProperty(value = "收费员")
    private String idFeecharger;

    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价")
    private String personpricelimit;

    @Excel(name = "实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @Excel(name = "体检类型", readConverterExp = "0=健康体检,1=职业体检,2=综合,3=复查")
    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "分组类型")
    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @Excel(name = "工作单位")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检卡号")
    @ApiModelProperty(value = "体检卡")
    private String tjk;

    @Excel(name = "套餐")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;


    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @ApiModelProperty(value = "体检者记时器开始")
    private Date timingstartedat;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

}
