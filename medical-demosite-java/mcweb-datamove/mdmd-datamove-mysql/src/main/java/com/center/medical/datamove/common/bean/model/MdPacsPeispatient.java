package com.center.medical.datamove.common.bean.model;


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
 * PACS-体检者表(MdPacsPeispatient)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
@Data
@TableName("md_pacs_peispatient")
@ApiModel(value = "MdPacsPeispatient", description = "PACS-体检者表实体类")
public class MdPacsPeispatient extends Model<MdPacsPeispatient> implements Serializable {
    private static final long serialVersionUID = -45704669228886189L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "XID")
    private String id;

    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "预约")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "已登记")
    private String fRegistered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "体检类型ID")
    private String idExamtype;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "pacs登记页面已登记")
    private Double idExamplace;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "客户证件类型，详见：com.world.center.bean.enums.CusCardType")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "黑名单备注")
    private String isHmdb;

    @ApiModelProperty(value = "黑名单")
    private Integer isHmd;

    @ApiModelProperty(value = "健康体检状态，详见：com.world.center.bean.enums.jktjzt")
    private Integer jktjzt;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;
}
