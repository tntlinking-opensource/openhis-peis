package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室工作量 分页返回数据
 */
@Data
public class DivisionWorkVo implements Serializable {
    private static final long serialVersionUID = 7739396044515423615L;

    @Excel(name = "序列")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检科室")
    @ApiModelProperty(value = "division0")
    private String division0;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "division1")
    private String division1;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "division2")
    private String division2;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "division3")
    private String division3;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "division4")
    private String division4;

    @Excel(name = "联系电话")
    @ApiModelProperty(value = "division15")
    private String division15;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "会员等级",readConverterExp = "1=普通会员,2=VIP,3=VVIP")
    @ApiModelProperty(value = "division14")
    private String division14;

    @Excel(name = "团检单位")
    @ApiModelProperty(value = "division5")
    private String division5;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "division6")
    private String division6;

    @Excel(name="登记日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "division7")
    private String division7;

    @Excel(name="体检时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "division8")
    private String division8;

    @Excel(name="录入人")
    @ApiModelProperty(value = "division9")
    private String division9;

    @Excel(name="医师")
    @ApiModelProperty(value = "division10")
    private String division10;

    @Excel(name="审核人")
    @ApiModelProperty(value = "审核人")
    private String auditName;

    @Excel(name="审核情况",readConverterExp = "null=未审核,0=未审核,1=已审核")
    @ApiModelProperty(value = "division11")
    private String division11;

    @Excel(name="审核日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "division12")
    private String division12;

    @Excel(name="小结")
    @ApiModelProperty(value = "division13")
    private String division13;

    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;


}
