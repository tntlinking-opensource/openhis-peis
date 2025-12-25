package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者(BkPatient)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_bk_patient")
@ApiModel(value = "BkPatient", description = "体检者实体类")
public class BkPatient extends Model<BkPatient> implements Serializable {
    private static final long serialVersionUID = 462593614001551189L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "客户证件类型：1.身份证 2.护照 6.军人证  7.港澳通行证/回乡证或台胞证")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "总检结论")
    private String suggestion;
}
