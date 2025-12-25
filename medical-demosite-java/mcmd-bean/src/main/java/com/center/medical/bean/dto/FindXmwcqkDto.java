package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FindXmwcqkDto implements Serializable {
    private static final long serialVersionUID = -2060872233078721509L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别id")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "团体ID(团检预约需要)")
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
    private String examFeeItemName;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "健康体检状态")
    private Integer jktjzt;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "体检类型ID 0健康1职业2复查")
    private String idExamtype;

    @ApiModelProperty(value = "补检体检号（上一次体检号）")
    private String insuranceno;
}
