package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CDC职业病直报数据查询 返回数据
 */
@Data
public class CdcPageVo implements Serializable {
    private static final long serialVersionUID = 6456467049651983254L;

    @Excel(name = "体检时间")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "用人单位")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;


    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系方式")
    private String phone;

    @Excel(name = "体检类型")
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "总工龄年")
    @ApiModelProperty(value = "总工龄年")
    private String zglyear;

    @Excel(name = "总工龄年")
    @ApiModelProperty(value = "总工龄月")
    private String zglmonth;

    @Excel(name = "接害因素")
    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @Excel(name = "接害工龄年")
    @ApiModelProperty(value = "接害工龄年")
    private String jhglyear;

    @Excel(name = "接害工龄月")
    @ApiModelProperty(value = "接害工龄月")
    private String jhglmonth;

    @Excel(name = "车间")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "工种")
    @ApiModelProperty(value = "工种")
    private String trades;


    @Excel(name = "噪声双耳高频平均听阈（校正值）")
    @ApiModelProperty(value = "电测听")
    private String dct;

    @Excel(name = "心电图")
    @ApiModelProperty(value = "心电图")
    private String xdt;


    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;



    @Excel(name = "是否复查")
    @ApiModelProperty(value = "是否复查")
    private String isfc;

    @Excel(name = "职业问诊")
    @ApiModelProperty(value = "症状")
    private String symptom;


    @Excel(name = "体检结论")
    @ApiModelProperty(value = "结论")
    private String occupationSummary;



    //电测听
    @Excel(name = "左耳气导500Hz")
    @ApiModelProperty(value = "气导左耳500Hz")
    private Double airLeft500;

    @Excel(name = "左耳气导1000Hz")
    @ApiModelProperty(value = "气导左耳1000Hz")
    private Double airLeft1000;

    @Excel(name = "左耳气导2000Hz")
    @ApiModelProperty(value = "气导左耳2000Hz")
    private Double airLeft2000;

    @Excel(name = "左耳气导3000Hz")
    @ApiModelProperty(value = "气导左耳3000Hz")
    private Double airLeft3000;

    @Excel(name = "左耳气导4000Hz")
    @ApiModelProperty(value = "气导左耳4000Hz")
    private Double airLeft4000;

    @Excel(name = "左耳气导6000Hz")
    @ApiModelProperty(value = "气导左耳6000Hz")
    private Double airLeft6000;

    @Excel(name = "右耳气导500Hz")
    @ApiModelProperty(value = "气导右耳500Hz")
    private Double airRight500;

    @Excel(name = "右耳气导1000Hz")
    @ApiModelProperty(value = "气导右耳1000Hz")
    private Double airRight1000;

    @Excel(name = "右耳气导2000Hz")
    @ApiModelProperty(value = "气导右耳2000Hz")
    private Double airRight2000;

    @Excel(name = "右耳气导3000Hz")
    @ApiModelProperty(value = "气导右耳3000Hz")
    private Double airRight3000;

    @Excel(name = "右耳气导4000Hz")
    @ApiModelProperty(value = "气导右耳4000Hz")
    private Double airRight4000;

    @Excel(name = "右耳气导6000Hz")
    @ApiModelProperty(value = "气导右耳6000Hz")
    private Double airRight6000;


    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "职业报告url")
    private String urlPdf;
}
