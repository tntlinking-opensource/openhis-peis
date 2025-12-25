package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登记保存更新数据
 */
@Data
public class PrFormdataDto implements Serializable {
    private static final long serialVersionUID = -2847679324741055807L;


    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "预约")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "客户证件类型，详见：CusCardType")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "黑名单")
    private Integer isHmd;

    @ApiModelProperty(value = "黑名单备注")
    private String isHmdb;

    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "会员等级")
    private String memberlevel;

    @ApiModelProperty(value = "黑名单备注")
    private String hmdbz;

    @ApiModelProperty(value = "成员创建")
    private String membercreate;

    @ApiModelProperty(value = "输入码")
    private String inputCode;


    @ApiModelProperty(value = "团检是否存在现金收费(1:现金 0：统收)")
    private String org;

    @ApiModelProperty(value = "tongFei")
    private String tongFei;


    @ApiModelProperty(value = "备选数量")
    private String bxcount;
}
