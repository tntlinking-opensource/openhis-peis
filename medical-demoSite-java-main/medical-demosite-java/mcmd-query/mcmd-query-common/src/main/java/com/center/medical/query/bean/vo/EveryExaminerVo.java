package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日体检明细 分页返回数据
 */
@Data
public class EveryExaminerVo implements Serializable {
    private static final long serialVersionUID = 8220082156946326607L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @Excel(name = "SABC级别")
    @ApiModelProperty(value = "SABC级别")
    private String sabc;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "体检团体")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "付款方式")
    private String idPayway;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "团体预订", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "预定（已备单）")
    private Integer fIsforprepare;

    @ApiModelProperty(value = "是否预约：0.未预约 1.已预约")
    private Integer fIsforreserve;

    @Excel(name = "登记", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @Excel(name = "已收费", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "已收费")
    private Integer fFeecharged;

    @Excel(name = "原价合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价合计")
    private String yjhj;

    @Excel(name = "应付合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "应付金额")
    private Double moneyamount;

    @Excel(name = "记账未结")
    @ApiModelProperty(value = "记账未结（导出使用）")
    private String jzwjdc;

    @Excel(name = "折扣率")
    @ApiModelProperty(value = "折扣率")
    private Double zkl;

    @ApiModelProperty(value = "实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "指引单回收")
    private Integer fGuidancereturned;

    @Excel(name = "分检完成", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @Excel(name = "总检完成", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "总检完成")
    private Double fFinalexamed;

    @Excel(name = "职业总检完成", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "职业体检状态，状态大于等于1总检完成 1 ，其他 0")
    private Integer FFinalexamedZy;

    @Excel(name = "报告已打", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "报告已打印")
    private Integer fReportprinted;

    @Excel(name = "职业报告已打", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "职业报告打印次数,打印过1，没打印过0")
    private Integer FReportprintedZy;

    @Excel(name = "结案", readConverterExp = "null=否,0=否,1=是,2=是,3=是,4=是,5=是,6=是,7=是,8=是,9=是,10=是,11=是,12=是,13=是")
    @ApiModelProperty(value = "结案")
    private Integer fClosed;

    @Excel(name = "体检分类", readConverterExp = "0=个检,1=团检")
    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer useCodeHiden;

    @Excel(name = "登记时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "登记员")
    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "电话")
    @ApiModelProperty(value = "购卡人手机号")
    private String phone;

    @Excel(name = "套餐")
    @ApiModelProperty(value = "套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "1 在线，其他 归档")
    private String position;


    @ApiModelProperty(value = "开单医生名称")
    private String kaidan;


    @ApiModelProperty(value = "jzwj")
    private String jzwj;


}


