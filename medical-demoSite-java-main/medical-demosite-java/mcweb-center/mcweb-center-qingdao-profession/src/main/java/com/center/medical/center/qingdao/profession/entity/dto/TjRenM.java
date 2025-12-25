package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Tj_RenM
 */
@Data
public class TjRenM implements Serializable {
    private static final long serialVersionUID = -8479216382211691367L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "地区")
    private String resarea;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "体检团体")
    private String orgName;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "总工龄")
    private String zgl;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "登记员")
    private String docreg;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "复查体检号（第一次的体检号）")
    private String inpatientno;

    @ApiModelProperty(value = "体检类型，0健康1职业2综合")
    private String idExamtype;

    @ApiModelProperty(value = "职业总检医生")
    private String zjys;

    @ApiModelProperty(value = "职业总检时间")
    private Date dateregisternotime;

    @ApiModelProperty(value = "zySummary（职业总检）")
    private String summaryId;

    @ApiModelProperty(value = " 体检者照片")
    private String picture;

    @ApiModelProperty(value = "结论")
    private String verdict;

    @ApiModelProperty(value = "综述")
    private String summarize;

    @ApiModelProperty(value = "总检建议")
    private String offer;

    @ApiModelProperty(value = "职业报告结论词")
    private String reportConclusions;

    @ApiModelProperty(value = "社会信用代码")
    private String socialCreditCode;
}
