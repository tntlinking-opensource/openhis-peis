package com.center.medical.sellcrm.bean.dto;

import com.center.medical.bean.enums.MedicalType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者基本信息保存（预登记）参数
 */
@Data
public class PaGridDataDto implements Serializable {
    private static final long serialVersionUID = 6045833923255792309L;

    @JsonProperty("groupName")
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "性别")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "婚姻id")
    private Integer idMarriage;

    @ApiModelProperty(value = "民族id")
    private String idNation;

    @ApiModelProperty(value = "文化程度，详见：CulturalLevel")
    private Integer cultural;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;


    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "工号")
    private String workno;

    @ApiModelProperty(value = "统收限额")
    private Double tsLimit;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "分中心ID")
    private String hospitalcode;

    @ApiModelProperty(value = "工种id  base_worktype")
    private String worktypeId;

    @ApiModelProperty(value = "SABC等级")
    private String sabc;
}
