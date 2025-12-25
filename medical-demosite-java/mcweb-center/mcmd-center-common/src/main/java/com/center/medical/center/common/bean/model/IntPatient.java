package com.center.medical.center.common.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

//@ApiModel("中间库")
@TableName("IntPatient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntPatient implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="ID_IntPatient",type = IdType.AUTO)
    private Integer idIntPatient;// 主键，自增长
    @TableField("HospitalCode")
    private String hospitalCode;// 医院编码 默认：SDQDZK
    @TableField("F_UsePatientAsOrg")
    private Boolean FUsePatientAsOrg;// 默认：0
    @TableField("ID_Patient")
    private Integer idPatient;// 登记号 8位体检号转int类型
    @TableField("StrIDPatient")
    private String strIdpatient;// 完整体检号
    @TableField("PatientCode")
    private String patientCode;// 8位登记号检验科lis软件窗口最多识别8位，多余的将截断  数据接口主要字段
    @TableField("PatientCodeHiden")
    private String patientCodeHiden;// 时间+流水号   可为年月日+流水号（5位）标软4位北斗6位
    @TableField("F_UseCodeHiden")
    private Boolean FUseCodeHiden;// 默认为：0
    @TableField("PatientCardNo")
    private String patientCardNo;
    @TableField("InPatientNo")
    private String inPatientNo;
    @TableField("OutPatientNo")
    private String outPatientNo;
    @TableField("IDCardNo")
    private String idcardNo;// 证件号  身份证号
    @TableField("PatientBizNo")
    private String patientBizNo;
    @TableField("PatientName")
    private String patientName;// 姓名
    @TableField("ID_PatientArchive")
    private Integer idPatientArchive;// 档案号
    @TableField("PatientArchiveNo")
    private String patientArchiveNo;
    @TableField("PatientRequestNo")
    private String patientRequestNo;
    @TableField("Input_Code")
    private String inputCode;// 拼音码
    @TableField("Sex")
    private String sex;// 性别
    @TableField("BirthDate")
    private Timestamp birthDate;// 出生日期
    @TableField("Age")
    private Integer age;// 年龄
    @TableField("AgeUnit")
    private String ageUnit;// 年龄单位  默认‘岁’
    @TableField("AgeOfReal")
    private Double ageOfReal;// 年龄
    @TableField("Marriage")
    private String marriage;// 婚姻状况 已婚、未婚、null
    @TableField("Profession")
    private String profession;
    @TableField("ResAddress")
    private String resAddress;// 家庭住址
    @TableField("Phone")
    private String phone;// 电话  可为空
    @TableField("Email")
    private String email;
    @TableField("ID_Org")
    private Integer idOrg;
    @TableField("ID_OrgReservation")
    private Integer idOrgReservation;
    @TableField("Org_Name")
    private String orgName;// 单位名称    可为空
    @TableField("Org_Code")
    private String orgCode;// 单位编码  可为空
    @TableField("Org_Depart")
    private String orgDepart;// 单位车间部门  可为空
    @TableField("Org_DepartSubA")
    private String orgDepartSubA;
    @TableField("Org_DepartSubB")
    private String orgDepartSubB;
    @TableField("Org_DepartSubC")
    private String orgDepartSubC;
    @TableField("Org_DepartSubD")
    private String orgDepartSubD;
    @TableField("Org_DepartSubE")
    private String orgDepartSubE;
    @TableField("Org_DepartAll")
    private String orgDepartAll;// 单位车间部门   可为空
    @TableField("NumDay")
    private Integer numDay;// 当天编号  PatientCodeHiden与其有关联
    @TableField("ExamType_Name")
    private String examTypeName;// 体检类型名称   健康、职业等体积类型
    @TableField("FeeType_Code")
    private String feeTypeCode;// 费用类型编码    可为空：NULL
    @TableField("FeeType_Name")
    private String feeTypeName;// 费用类型名称    可为空：NULL
    @TableField("F_Registered")
    private Boolean FRegistered;// 登记标志 此字段为1，表示登记完成
    @TableField("DateRegister")
    private Timestamp dateRegister;// 登记时间
    @TableField("DoctorReg")
    private String doctorReg;// 登记操作员名称
    @TableField("DoctorRegCode")
    private String doctorRegCode;// 操作员编码   可为空
    @TableField("DoctorOpen")
    private String doctorOpen;// 销售员名称
    @TableField("DoctorOpenCode")
    private String doctorOpenCode;// 销售员编码  大部分可为空
    @TableField("F_FeeCharged")
    private Boolean FFeeCharged;// 收费标志（？）  默认：1
    @TableField("ParsedSuiteAndFI")
    private String parsedSuiteAndFi;
    @TableField("ParsedSuiteAndFILab")
    private String parsedSuiteAndFilab;
    @TableField("DateCreatedPeisPatient")
    private Timestamp dateCreatedPeisPatient;// 最初创建时间
    @TableField("F_Paused")
    private Boolean FPaused;// 默认：0
    @TableField("F_Transfered")
    private Boolean FTransfered;// 是否核收标志   默认：0，0时可以再次核收
    @TableField("DT_LastBySync")
    private Timestamp dtLastBySync;
    @TableField("DT_LastByPEIS")
    private Timestamp dtLastByPeis;// 最后修改时间    被体检软件操作
    @TableField("DT_LastByHIS")
    private Timestamp dtLastByHis;
    @TableField("TransferInsOrUpdTimes")
    private Integer transferInsOrUpdTimes;
    @TableField("F_NEWPACS")
    private Boolean newpacs;//是否是新pacs插入(新增字段)
    @TableField("ID_PATIENTCLASS")
    private Integer idPatientclass;
}