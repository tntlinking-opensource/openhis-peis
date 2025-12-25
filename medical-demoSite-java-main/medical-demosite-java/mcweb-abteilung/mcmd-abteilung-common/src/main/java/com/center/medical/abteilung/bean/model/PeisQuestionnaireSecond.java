package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康体检问卷实体类
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peis_questionnaire_second")
@ApiModel(value = "PeisQuestionnaireSecond", description = "健康体检问卷实体类")
public class PeisQuestionnaireSecond extends Model<PeisQuestionnaireSecond> implements Serializable {
    private static final long serialVersionUID = -89049622710660233L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别 0男1女")
    private String idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "民族  1汉族0少数民族")
    private String isHan;

    @ApiModelProperty(value = "省id")
    private String idProvince;

    @ApiModelProperty(value = "市id")
    private String idCity;

    @ApiModelProperty(value = "区id")
    private String idArea;

    @ApiModelProperty(value = "婚姻")
    private Integer idMarriage;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "family1")
    private Integer family1;

    @ApiModelProperty(value = "family2")
    private String family2;

    @ApiModelProperty(value = "family3")
    private String family3;

    @ApiModelProperty(value = "family4")
    private Integer family4;

    @ApiModelProperty(value = "present1")
    private Integer present1;

    @ApiModelProperty(value = "present2")
    private String present2;

    @ApiModelProperty(value = "present3")
    private Integer present3;

    @ApiModelProperty(value = "allergy1")
    private Integer allergy1;

    @ApiModelProperty(value = "medication1")
    private Integer medication1;

    @ApiModelProperty(value = "medication2")
    private String medication2;

    @ApiModelProperty(value = "operation1")
    private Integer operation1;

    @ApiModelProperty(value = "body1")
    private Integer body1;

    @ApiModelProperty(value = "body2")
    private Integer body2;

    @ApiModelProperty(value = "body3")
    private Integer body3;

    @ApiModelProperty(value = "body4")
    private Integer body4;

    @ApiModelProperty(value = "body5")
    private Integer body5;

    @ApiModelProperty(value = "body6")
    private Integer body6;

    @ApiModelProperty(value = "body7")
    private Integer body7;

    @ApiModelProperty(value = "body8")
    private Integer body8;

    @ApiModelProperty(value = "body9")
    private Integer body9;

    @ApiModelProperty(value = "smoke1")
    private Integer smoke1;

    @ApiModelProperty(value = "smoke2")
    private Integer smoke2;

    @ApiModelProperty(value = "smoke3")
    private Integer smoke3;

    @ApiModelProperty(value = "smoke4")
    private Integer smoke4;

    @ApiModelProperty(value = "drink1")
    private Integer drink1;

    @ApiModelProperty(value = "sport1")
    private Integer sport1;

    @ApiModelProperty(value = "sport2")
    private Integer sport2;

    @ApiModelProperty(value = "sport3")
    private Integer sport3;

    @ApiModelProperty(value = "environment1")
    private String environment1;

    @ApiModelProperty(value = "sleep1")
    private Integer sleep1;

    @ApiModelProperty(value = "sleep2")
    private Integer sleep2int;

    @ApiModelProperty(value = "examination1")
    private Integer examination1;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "sleep2Other")
    private String sleep2Other;

    @ApiModelProperty(value = "medication2Other")
    private String medication2Other;

    @ApiModelProperty(value = "present2Other")
    private String present2Other;

    @ApiModelProperty(value = "family3Other")
    private String family3Other;

    @ApiModelProperty(value = "family2Other")
    private String family2Other;

    @ApiModelProperty(value = "operation1Other")
    private String operation1Other;

    @ApiModelProperty(value = "微信小程序用户id")
    private String appUserId;

    @ApiModelProperty(value = "sleep2")
    private String sleep2;

    @ApiModelProperty(value = "body10")
    private String body10;

    @ApiModelProperty(value = "patientbizno")
    private String patientbizno;

    @ApiModelProperty(value = "bmi")
    private Double bmi;

    @ApiModelProperty(value = "身高m")
    private Double height;

    @ApiModelProperty(value = "体重kg")
    private Double weight;

    @TableField(exist = false)
    @ApiModelProperty(value = "科室id")
    private String ksId;


    @ApiModelProperty(value = "${column.comment}")
    private String operation2;

    @ApiModelProperty(value = "${column.comment}")
    private String operation2other;

    @ApiModelProperty(value = "${column.comment}")
    private String birth1;

    @ApiModelProperty(value = "${column.comment}")
    private String birth2;

    @ApiModelProperty(value = "${column.comment}")
    private String birth2other;

    @ApiModelProperty(value = "${column.comment}")
    private String birth3;

    @ApiModelProperty(value = "${column.comment}")
    private String birth4;

    @ApiModelProperty(value = "${column.comment}")
    private String birth5;

    @ApiModelProperty(value = "${column.comment}")
    private String birth6;

    @ApiModelProperty(value = "${column.comment}")
    private String birth7;

    @ApiModelProperty(value = "${column.comment}")
    private String birth8;

    @ApiModelProperty(value = "${column.comment}")
    private String birth9;

    @ApiModelProperty(value = "${column.comment}")
    private String birth10;

    @ApiModelProperty(value = "${column.comment}")
    private String body21;

    @ApiModelProperty(value = "${column.comment}")
    private String body22;

    @ApiModelProperty(value = "${column.comment}")
    private String body23;

    @ApiModelProperty(value = "${column.comment}")
    private String body24;

    @ApiModelProperty(value = "${column.comment}")
    private String body25;

    @ApiModelProperty(value = "${column.comment}")
    private String body26;

    @ApiModelProperty(value = "${column.comment}")
    private String body31;

    @ApiModelProperty(value = "${column.comment}")
    private String body32;

    @ApiModelProperty(value = "${column.comment}")
    private String body33;

    @ApiModelProperty(value = "${column.comment}")
    private String body41;

    @ApiModelProperty(value = "${column.comment}")
    private String body71;

    @ApiModelProperty(value = "${column.comment}")
    private String body72;

    @ApiModelProperty(value = "${column.comment}")
    private String body73;

    @ApiModelProperty(value = "${column.comment}")
    private String body81;

    @ApiModelProperty(value = "${column.comment}")
    private String body82;

    @ApiModelProperty(value = "${column.comment}")
    private String body91;

    @ApiModelProperty(value = "${column.comment}")
    private String body92;

    @ApiModelProperty(value = "${column.comment}")
    private String eat1;

    @ApiModelProperty(value = "${column.comment}")
    private String eat2;

    @ApiModelProperty(value = "${column.comment}")
    private String eat3;

    @ApiModelProperty(value = "${column.comment}")
    private String eat4;

    @ApiModelProperty(value = "${column.comment}")
    private String eat5;

    @ApiModelProperty(value = "${column.comment}")
    private String eat6;

    @ApiModelProperty(value = "${column.comment}")
    private String eat7;

    @ApiModelProperty(value = "${column.comment}")
    private String eat8;

    @ApiModelProperty(value = "${column.comment}")
    private String eat9;

    @ApiModelProperty(value = "${column.comment}")
    private String eat10;

    @ApiModelProperty(value = "${column.comment}")
    private String eat11;

    @ApiModelProperty(value = "${column.comment}")
    private String eat12;

    @ApiModelProperty(value = "${column.comment}")
    private String eat13;

    @ApiModelProperty(value = "${column.comment}")
    private String eat14;

    @ApiModelProperty(value = "${column.comment}")
    private String eat15;

    @ApiModelProperty(value = "${column.comment}")
    private String eat16;

    @ApiModelProperty(value = "${column.comment}")
    private String eat17;

    @ApiModelProperty(value = "${column.comment}")
    private String eat18;

    @ApiModelProperty(value = "${column.comment}")
    private String eat19;

    @ApiModelProperty(value = "${column.comment}")
    private String drink2;

    @ApiModelProperty(value = "${column.comment}")
    private String drink3;

    @ApiModelProperty(value = "${column.comment}")
    private String drink4;

    @ApiModelProperty(value = "${column.comment}")
    private String drink5;

    @ApiModelProperty(value = "${column.comment}")
    private String sport11;

    @ApiModelProperty(value = "${column.comment}")
    private String sport5;

    @ApiModelProperty(value = "${column.comment}")
    private String sport6;

    @ApiModelProperty(value = "${column.comment}")
    private String sport7;

    @ApiModelProperty(value = "${column.comment}")
    private String sport8;

    @ApiModelProperty(value = "${column.comment}")
    private String sport9;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit1;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit2;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit3;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit4;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit5;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit6;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit7;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit8;

    @ApiModelProperty(value = "${column.comment}")
    private String spirit9;

    @ApiModelProperty(value = "${column.comment}")
    private String sleep3;

    @ApiModelProperty(value = "${column.comment}")
    private String sleep4;

    @ApiModelProperty(value = "${column.comment}")
    private String examination2;

    @ApiModelProperty(value = "${column.comment}")
    private String examination3;

    @ApiModelProperty(value = "${column.comment}")
    private String examination4;

    @ApiModelProperty(value = "${column.comment}")
    private String examination5;

    @ApiModelProperty(value = "${column.comment}")
    private String examination6;

    @ApiModelProperty(value = "${column.comment}")
    private String examination7;

    @ApiModelProperty(value = "${column.comment}")
    private String examination8;

    @ApiModelProperty(value = "${column.comment}")
    private String examination9;

    @ApiModelProperty(value = "${column.comment}")
    private String examination10;

    @ApiModelProperty(value = "${column.comment}")
    private String examination11;

    @ApiModelProperty(value = "${column.comment}")
    private String examination12;

    @ApiModelProperty(value = "${column.comment}")
    private String examination13;

    @ApiModelProperty(value = "${column.comment}")
    private String examination14;

    @ApiModelProperty(value = "${column.comment}")
    private String examination15;

    @ApiModelProperty(value = "${column.comment}")
    private String examination16;

    @ApiModelProperty(value = "${column.comment}")
    private String examination17;

    @ApiModelProperty(value = "${column.comment}")
    private String examination18;

    @ApiModelProperty(value = "${column.comment}")
    private String examination19;

    @ApiModelProperty(value = "${column.comment}")
    private String birth8other;

    @ApiModelProperty(value = "${column.comment}")
    private String present21;

    @ApiModelProperty(value = "${column.comment}")
    private String present21other;

    @ApiModelProperty(value = "${column.comment}")
    private String allergy2;
}
