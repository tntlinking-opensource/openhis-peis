package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收费日报每日客服报表统计返回数据
 */
@Data
public class CQListVo implements Serializable {
    private static final long serialVersionUID = 1131470181550918758L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @Excel(name = "收费日期")
    @ApiModelProperty(value = "缴费时间")
    private String feechargetime;


    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @Excel(name = "收费员")
    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @Excel(name = "实收")
    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;


    @Excel(name = "分组类型")
    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @Excel(name = "工作单位")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "身份证号")
    private String cardno;

    @Excel(name = "套餐")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "体检类型", readConverterExp = "0=健康体检,1=职业体检,2=综合,3=复查")
    @ApiModelProperty(value = "体检类型")
    private String idExamtype;


    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;


    @Excel(name = "姓名")
    @ApiModelProperty(value = "电话")
    private String phone;


    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private Integer intId;

    @ApiModelProperty(value = "体检号生成时间")
    private Date timingstartedat;

    @Excel(name = "体检卡号")
    @ApiModelProperty(value = "体检卡")
    private String tjk;



    @ApiModelProperty(value = "订单的销售经理，当是补检时取这个")
    private String xsjl;
}
