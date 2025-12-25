package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 体检者沃德国际健康问卷(PeisQuestionnaire)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:58
 */
@Data
@TableName("PEIS_QUESTIONNAIRE")
@ApiModel(value = "PeisQuestionnaire", description = "体检者沃德国际健康问卷实体类")
public class PeisQuestionnaire extends Model<PeisQuestionnaire> implements Serializable {
    private static final long serialVersionUID = 175490001002713401L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别id")
    private String idSex;

    @ApiModelProperty(value = "民族id")
    private String idNation;

    @ApiModelProperty(value = "婚姻id")
    private String idMarriage;

    @ApiModelProperty(value = "长期居住地")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "有无过敏史 0无1有")
    private Integer hasAllergyHistory;

    @ApiModelProperty(value = "体检报告（0纸质版1电子版）")
    private Integer reportType;

    @ApiModelProperty(value = "早餐（0中餐1西餐）")
    private Integer breakfast;

    @ApiModelProperty(value = "个人疾病史ids")
    private String diseaseHistory;

    @ApiModelProperty(value = "既往史及现在正在治疗的疾病")
    private String phAndDiseases;

    @ApiModelProperty(value = "家族史ids")
    private String familyHistory;

    @ApiModelProperty(value = "是否吸烟（0否1是）")
    private Integer isSmoking;

    @ApiModelProperty(value = "吸烟x支/天")
    private String smokeNum;

    @ApiModelProperty(value = "是否饮酒（0否1是）")
    private Integer isDrinking;

    @ApiModelProperty(value = "饮酒种类ids")
    private String drinkType;

    @ApiModelProperty(value = "饮酒 x ml/周")
    private String drinkNum;

    @ApiModelProperty(value = "饮食习惯ids")
    private String eatingHabits;

    @ApiModelProperty(value = "运动情况id")
    private Integer sports;

    @ApiModelProperty(value = "睡眠质量ids")
    private String sleep;

    @ApiModelProperty(value = "心理情况id")
    private Integer psychological;

    @ApiModelProperty(value = "既往体检异常")
    private String historyAbnormal;

    @ApiModelProperty(value = "本次体检异常")
    private String abnormal;

    @ApiModelProperty(value = "是否有备孕计划（0否1是）")
    private Integer isPreparingPregnant;

    @ApiModelProperty(value = "是否在生理期（0否1是）")
    private Integer isHavingPeriod;

    @ApiModelProperty(value = "是否在孕期（0否1是）")
    private Integer isPregnant;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorname;

    @ApiModelProperty(value = "${column.comment}")
    private Date writeTime;
}
