package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业问诊(PeispatientConsultation)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_consultation")
@ApiModel(value = "PeispatientConsultation", description = "职业问诊实体类")
public class PeispatientConsultation extends Model<PeispatientConsultation> implements Serializable {
    private static final long serialVersionUID = -24276599673081133L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "既往病名(多个ID逗号连接)")
    private String everOfDisease;


    @ApiModelProperty(value = "月经及生育史-初潮")
    private String ccnl;

    @ApiModelProperty(value = "月经及生育史-经期")
    private String jq;

    @ApiModelProperty(value = "月经及生育史-周期")
    private String zq;

    @ApiModelProperty(value = "月经及生育史-停经年龄")
    private String tjnl;

    @ApiModelProperty(value = "月经及生育史-现有子女")
    private String familyNumber;

    @ApiModelProperty(value = "月经及生育史-早产")
    private String zc;

    @ApiModelProperty(value = "月经及生育史-死产")
    private String sc;

    @ApiModelProperty(value = "月经及生育史-流产")
    private String lc;

    @ApiModelProperty(value = "月经及生育史-先天畸形")
    private String jt;

    @ApiModelProperty(value = "月经及生育史-异常胎")
    private String ywrc;

    @ApiModelProperty(value = "吸烟史-吸烟情况 0：从不吸烟 ；1：偶尔吸烟；2：以往曾经吸烟，现已戒除；3：经常吸烟")
    private String abstainSmokeNote;

    @ApiModelProperty(value = "吸烟史-每天吸烟包数")
    private String everydaySmokeN;

    @ApiModelProperty(value = "吸烟史-吸烟年数")
    private String smokeYear;

    @ApiModelProperty(value = "饮酒史-是否不饮酒")
    private String noKissTheCup;

    @ApiModelProperty(value = "饮酒史-是否偶饮酒")
    private String betweenKissTheCup;

    @ApiModelProperty(value = "饮酒史-是否经常饮酒")
    private String evermoreKiss;

    @ApiModelProperty(value = "饮酒史-是否戒酒")
    private String abstainLostKiss;

    @ApiModelProperty(value = "饮酒史-饮酒年数")
    private String kissYearN;

    @ApiModelProperty(value = "饮酒史-饮酒量")
    private String kissAmount;

    @ApiModelProperty(value = "饮酒史-饮酒种类")
    private String kissType;

    @ApiModelProperty(value = "家族病史")
    private String familyOfDisease;

    @ApiModelProperty(value = "症状")
    private String symptom;

    @ApiModelProperty(value = "职业性问诊-是否已审核 0未审核，1已审核，2反审核")
    private Integer isAudit;

    @ApiModelProperty(value = "既往病备注")
    private String everOfDiseaseRemark;

    @ApiModelProperty(value = "其他")
    private String other;

    @ApiModelProperty(value = "饮酒史-饮酒月数")
    private String kissMonth;

    @ApiModelProperty(value = "吸烟史-吸烟月数")
    private String smokeMonth;

    @TableField(exist = false)
    @ApiModelProperty(value = "既往病名名(多个以逗号连接)")
    private String everOfDiseaseName;
}
