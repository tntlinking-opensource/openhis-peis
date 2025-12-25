package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//迟补检回访展示页面
@Data
public class ForinspectionViewVo implements Serializable {

    private static final long serialVersionUID = 7047266693340400564L;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "预约来检时间")
    @ApiModelProperty(value = "预约来检时间")
    private Date ljsj;


    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别",readConverterExp="0 = 男,1 = 女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "是否来检")
    private String isInspect;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "公司")
    @ApiModelProperty(value = "公司")
    private String orgName;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "本次回访人")
    @ApiModelProperty(value = "回访人")
    private String visitId;

    @Excel(name = "本次回访时间")
    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @Excel(name = "本次回访备注")
    @ApiModelProperty(value = "回访备注")
    private String memo;

    @Excel(name = "性别",readConverterExp="0=弃检,1=迟检来检,2=补检,3=看备注,4=已电话通知,5=补检来检,6=再通知,7=无")
    @ApiModelProperty(value = "预约是否来检")
    private Integer sflj;



    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "类型",readConverterExp="0=迟检,4=补检")
    @ApiModelProperty(value = "类型")
    private String type;

    @Excel(name = "登记时间")
    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @Excel(name = "体检类型")
    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "迟捡收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "未检项目（ID）")
    private String itemId;

    @ApiModelProperty(value = "体检类别")
    private String medicaltype;

    @ApiModelProperty(value = "预处理时间")
    private String preTime;

    @ApiModelProperty(value = "iszy")
    private Boolean iszy;
}
