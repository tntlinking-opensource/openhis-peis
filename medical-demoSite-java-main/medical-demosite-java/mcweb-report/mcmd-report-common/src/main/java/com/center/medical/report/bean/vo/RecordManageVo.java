package com.center.medical.report.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 对比报告 分页参数
 */

@Data
public class RecordManageVo implements Serializable {
    private static final long serialVersionUID = 7224340616956123109L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别 0男1女")
    private String idSex;

    @Excel(name="生日",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private String birthdate;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "会员类型")
    @ApiModelProperty(value = "会员类型，vip等")
    private String memberName;

    @Excel(name = "民族名称")
    @ApiModelProperty(value = "民族名称")
    private String nation;

    @ApiModelProperty(value = "民族ID")
    private String idNation;

    @Excel(name = "家庭住址")
    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "文化程度，详见：CulturalLevel")
    private Integer cultural;

    @Excel(name = "文化程度名称")
    @ApiModelProperty(value = "文化程度，详见：CulturalLevel")
    private String culturalName;

    @Excel(name = "性别" ,readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "黑名单")
    private String ishmd;

    @Excel(name = "黑名单备注")
    @ApiModelProperty(value = "黑名单备注")
    private String hmdbz;

    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;

    @Excel(name="建档时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private String createdate;

    @ApiModelProperty(value = "examcounts")
    private String examcounts;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "客户证件类型，详见：CusCardType")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;

    @ApiModelProperty(value = "金额")
    private Double money;

}
