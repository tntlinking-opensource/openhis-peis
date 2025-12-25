package com.center.medical.app.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序问卷(Questionnaire)表实体类
 *
 * @author ay
 * @since 2023-12-05 08:57:22
 */
@Data
@TableName("md_questionnaire")
@ApiModel(value = "Questionnaire", description = "小程序问卷实体类")
public class Questionnaire extends Model<Questionnaire> implements Serializable {
    private static final long serialVersionUID = -47393466620036206L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "性别 0男1女")
    private String idSex;


    @ApiModelProperty(value = "出生日期")
    private String birthdate;


    @ApiModelProperty(value = "身份证号")
    private String idcardno;


    @ApiModelProperty(value = "民族  1汉族0少数民族")
    private String isHan;


    @ApiModelProperty(value = "婚姻")
    private Integer idMarriage;


    @ApiModelProperty(value = "电话")
    private String phone;


    @ApiModelProperty(value = "文化水平，详见：com.center.medical.bean.enums.CulturalLevel")
    private Integer cultural;


    @ApiModelProperty(value = "职业")
    private String career;


    @ApiModelProperty(value = "家庭住址")
    private String homeAddress;


    @ApiModelProperty(value = "工作住址")
    private String workAddress;


    @ApiModelProperty(value = "创建者")
    private String creator;


    @ApiModelProperty(value = "${column.comment}")
    private String question1;


    @ApiModelProperty(value = "${column.comment}")
    private String question2;


    @ApiModelProperty(value = "${column.comment}")
    private String question3;


    @ApiModelProperty(value = "${column.comment}")
    private String question4;


    @ApiModelProperty(value = "${column.comment}")
    private String question5;


    @ApiModelProperty(value = "${column.comment}")
    private String question6;


    @ApiModelProperty(value = "${column.comment}")
    private String question7;


    @ApiModelProperty(value = "${column.comment}")
    private String question8;


    @ApiModelProperty(value = "${column.comment}")
    private String question9;


    @ApiModelProperty(value = "${column.comment}")
    private String question10;


    @ApiModelProperty(value = "${column.comment}")
    private String question11;


    @ApiModelProperty(value = "${column.comment}")
    private String question12;


    @ApiModelProperty(value = "${column.comment}")
    private String question13;


    @ApiModelProperty(value = "${column.comment}")
    private String question14;


    @ApiModelProperty(value = "${column.comment}")
    private String question15;


    @ApiModelProperty(value = "${column.comment}")
    private String question16;


    @ApiModelProperty(value = "${column.comment}")
    private String question17;


    @ApiModelProperty(value = "${column.comment}")
    private String question18;


    @ApiModelProperty(value = "${column.comment}")
    private String question19;


    @ApiModelProperty(value = "${column.comment}")
    private String question20;


    @ApiModelProperty(value = "${column.comment}")
    private String question21;


    @ApiModelProperty(value = "${column.comment}")
    private String question22;


    @ApiModelProperty(value = "${column.comment}")
    private String question23;


    @ApiModelProperty(value = "${column.comment}")
    private String question24;


    @ApiModelProperty(value = "${column.comment}")
    private String question25;


    @ApiModelProperty(value = "${column.comment}")
    private String question26;


    @ApiModelProperty(value = "${column.comment}")
    private String question27;


    @ApiModelProperty(value = "${column.comment}")
    private String question28;


    @ApiModelProperty(value = "${column.comment}")
    private String question29;


    @ApiModelProperty(value = "${column.comment}")
    private String question30;


    @ApiModelProperty(value = "居住时长")
    private String liveHours;


    @ApiModelProperty(value = "工作时长")
    private String workingHours;


    @ApiModelProperty(value = "分中心id")
    private String fzxid;


    @ApiModelProperty(value = "签名图片，有图片代表放弃回答问卷")
    private String pic;

    @ApiModelProperty(value = "客户证件类型")
    private Integer countreportoccupationxml;


    @ApiModelProperty(value = "类型 0问卷1满意度")
    private Integer type;


    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String branchName;


    @TableField(exist = false)
    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer fUsecodehiden;

}
