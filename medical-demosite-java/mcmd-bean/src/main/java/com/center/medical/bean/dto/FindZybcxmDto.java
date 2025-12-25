package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FindZybcxmDto implements Serializable {
    private static final long serialVersionUID = -1998662083097991364L;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "职业体检状态")
    private Integer zytjzt;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "已开始体检：0或null.否 1.是")
    private Integer fExamstarted;

    @ApiModelProperty(value = "第三方名称")
    private String typeName;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "职业总检时间")
    private Date dateregisternotime;

    @ApiModelProperty(value = "第三方名称")
    private String idcardno;



    @ApiModelProperty(value = "体检类型ID 0健康1职业2复查")
    private String idExamtype;

    @ApiModelProperty(value = "补检体检号（上一次体检号）")
    private String insuranceno;

}
