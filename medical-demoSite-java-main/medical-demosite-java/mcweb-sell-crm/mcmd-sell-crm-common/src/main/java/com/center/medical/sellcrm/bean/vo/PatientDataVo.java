package com.center.medical.sellcrm.bean.vo;

import com.center.medical.bean.enums.MedicalType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取分组下相应的人员信息
 */
@Data
public class PatientDataVo implements Serializable {
    private static final long serialVersionUID = -6914743145101284486L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;

    @ApiModelProperty(value = "民族ID")
    private String idNation;

    @ApiModelProperty(value = "文化水平，详见：com.center.medical.bean.enums.CulturalLevel")
    private Integer cultural;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "工号")
    private String workno;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    /**
     * @see MedicalType
     */
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "sms短信联系人")
    private String idContact;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "xzfzx")
    private String xzfzx;

    @ApiModelProperty(value = "统收限额（未用）")
    private Double tsLimit;

    @ApiModelProperty(value = "微信沃德小程序体检码")
    private String wechatCode;

    @ApiModelProperty(value = "微信沃德小程序是否已通知")
    private Integer isWechatNoticed;

}
