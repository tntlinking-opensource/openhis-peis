package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平安对账单 获取体检者数据 返回数据
 */
@Data
public class PatientListVo implements Serializable {
    private static final long serialVersionUID = 5321150779379963278L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "平安订单Id")
    @ApiModelProperty(value = "第三方平台（平安、康淘等）订单ID")
    private String patientnamereceipt;

    @Excel(name = "体检状态")
    @ApiModelProperty(value = "line")
    private String line;

    @Excel(name = "禁检")
    @ApiModelProperty(value = "禁检")
    private Integer fPaused;

    @Excel(name = "分组名称")
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "登记")
    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @Excel(name = "婚姻" ,readConverterExp = "1=未婚,2=已婚,3=离异,4=丧偶,5=其他")
    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "是否替检",readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "是否替检：0.否 1.是")
    private Integer countreportxml;

    @Excel(name = "原体检者")
    @ApiModelProperty(value = "替检人")
    private String tjr;

    @Excel(name = "套餐")
    @ApiModelProperty(value = "体检套餐名称")
    private String examName;

    @Excel(name = "原价")
    @ApiModelProperty(value = "金额")
    private Double price;

    @Excel(name = "套餐原价")
    @ApiModelProperty(value = "套餐原始价格")
    private Double tcyj;

    @Excel(name = "套餐优惠价")
    @ApiModelProperty(value = "折后价格")
    private Double tcyhj;

    @Excel(name = "统收实收")
    @ApiModelProperty(value = "统收实收")
    private Double ssts;

    @Excel(name = "其他实收")
    @ApiModelProperty(value = "其他实收")
    private Double ssqt;

    @Excel(name = "合计实收")
    @ApiModelProperty(value = "合计实收")
    private Double sshj;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name="体检时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @Excel(name = "登记员")
    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "支付方式名称")
    private String chargePayway;

    @Excel(name = "输入码")
    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @Excel(name = "档案号")
    @ApiModelProperty(value = "档案号")
    private String chiveNo;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "记账")
    @ApiModelProperty(value = "记账")
    private Double jz;

    @Excel(name = "记账人")
    @ApiModelProperty(value = "记账人")
    private String jzr;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "体检号生成人")
    @ApiModelProperty(value = "备单体检号生成人")
    private String guidancenote2;

    @ApiModelProperty(value = "linenum")
    private Integer linenum;


}
