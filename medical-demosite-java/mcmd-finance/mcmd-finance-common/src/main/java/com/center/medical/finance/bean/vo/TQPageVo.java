package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记账管理-记账结算分页返回数据
 */
@Data
public class TQPageVo implements Serializable {
    private static final long serialVersionUID = -5344653563215152101L;

    @Excel(name = "团体")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    @Excel(name = "记账未结")
    @ApiModelProperty(value = "记账未结")
    private Double paid;

    @Excel(name = "记账合计")
    @ApiModelProperty(value = "记账金额")
    private Double jzje;

    @Excel(name = "记账人")
    @ApiModelProperty(value = "记账人")
    private String jzr;

    @Excel(name = "记账单位")
    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @Excel(name = "审批人")
    @ApiModelProperty(value = "审批人")
    private String spr;

    @Excel(name = "记账日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "记账日期")
    private Date jzDate;

    @Excel(name = "结算金额")
    @ApiModelProperty(value = "结算金额")
    private String ypid;

    @Excel(name = "结算日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结算日期")
    private Date jsDate;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiParam(hidden = true)
    @Excel(name = "结算状态")
    @ApiModelProperty(value = "结算状态")
    private String jszt;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费员名称")
    private String feechargerName;

}
