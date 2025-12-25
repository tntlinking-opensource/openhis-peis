package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业健康检查结果结论附表 返回数据
 */
@Data
public class PositiveQueryVo implements Serializable {
    private static final long serialVersionUID = -977472709387609855L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "登记时间", dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "体检类别", readConverterExp = "0=上岗前,1=在岗期间,2=离岗时,3=离岗后,4=应急")
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @Excel(name = "复查次数")
    @ApiModelProperty(value = "当前体检号是第几次复查")
    private Integer counterreportprinted;

    @Excel(name = "总工龄(年)")
    @ApiModelProperty(value = "总工龄")
    private String zgl;

    @Excel(name = "接害工龄(年)")
    @ApiModelProperty(value = "接害工龄")
    private String jhgl;

    @Excel(name = "工种")
    @ApiModelProperty(value = "工种")
    private String trades;

    @Excel(name = "危害因素")
    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @Excel(name = "检查结果")
    @ApiModelProperty(value = "是否阳性结果")
    private String posistive;

    @Excel(name = "检查结论")
    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @Excel(name = "处理意见")
    @ApiModelProperty(value = "处理意见")
    private String summaryText;

    @Excel(name = "体检单位")
    @ApiModelProperty(value = "团体名称")
    private String org;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "复查项目")
    @ApiModelProperty(value = "检查项目信息")
    private String items;

    @Excel(name = "末次体检")
    @ApiModelProperty(value = "是否是最后一次体检")
    private String isFinal;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;




}
