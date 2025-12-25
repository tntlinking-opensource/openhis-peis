package com.center.medical.reception.bean.model;


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
 * QT体检者表(Peispatient)表实体类
 *
 * @author ay
 * @since 2023-08-12 11:54:59
 */
@Data
@TableName("PEISPATIENT")
@ApiModel(value = "Peispatient", description = "QT体检者表实体类")
public class OrPeispatient extends Model<OrPeispatient> implements Serializable {
    private static final long serialVersionUID = 104103808269198955L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "XID")
    private String id;


    @ApiModelProperty(value = "ID_CIS")
    private String idCis;

    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "档案代码")
    private String patientarchiveno;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "业务编号")
    private String patientbizno;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "任务编号（订单号）")
    private String numorgresv;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "预定任务ID")
    private String idOrgreservation;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "是否有隐私项目：0.无 1.有")
    private String havePrivate;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "统收限额")
    private String personpricelimit;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @ApiModelProperty(value = "民族ID")
    private String idNation;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "通知方式ID")
    private String idInformway;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "地区ID")
    private String idResarea;

    @ApiModelProperty(value = "地区")
    private String resarea;

    @ApiModelProperty(value = "预定（已备单）")
    private String fIsforprepare;

    @ApiModelProperty(value = "预约")
    private String fIsforreserve;

    @ApiModelProperty(value = "已登记")
    private String fRegistered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "应付金额")
    private String moneyamount;

    @ApiModelProperty(value = "实付金额")
    private String moneyamountpaid;

    @ApiModelProperty(value = "指引单说明（导引单备注）")
    private String guidancenote;

    @ApiModelProperty(value = "工号")
    private String workno;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "体检类型ID")
    private String idExamtype;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "EXAMSUITE_ALIAS")
    private String examsuiteAlias;

    @ApiModelProperty(value = "开单医生ID2总检锁定人")
    private String idDoctorapply;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "指引单已打")
    private String fGuidanceprinted;

    @ApiModelProperty(value = "已开始体检")
    private String fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private String fReadytofinal;

    @ApiModelProperty(value = "收费员ID")
    private String idDoctorfee;

    @ApiModelProperty(value = "禁检")
    private String fPaused;

    @ApiModelProperty(value = "总检锁定")
    private String fFinallocked;

    @ApiModelProperty(value = "总检完成")
    private String fFinalexamed;

    @ApiModelProperty(value = "总检医生ID")
    private String idDoctorfinal;

    @ApiModelProperty(value = "总检医生")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "总检时间")
    private Date datefinalexamed;

    @ApiModelProperty(value = "重大疾病")
    private String fIssevere;

    @ApiModelProperty(value = "结案")
    private String fClosed;

    @ApiModelProperty(value = "结案日期")
    private Date dateclosed;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "重症已通知")
    private String fSevereinformed;

    @ApiModelProperty(value = "健康知识")
    private String knowledge;

    @ApiModelProperty(value = "体检者记时器开始")
    private Date timingstartedat;

    @ApiModelProperty(value = "医院代码")
    private String hospitalcode;

    @ApiModelProperty(value = "ID_EXAMPLACE")
    private String idExamplace;

    @ApiModelProperty(value = "PARSEDASSIGNEDSUITEANDFI")
    private String parsedassignedsuiteandfi;

    @ApiModelProperty(value = "PARSEDASSIGNEDGROUPANDFI")
    private String parsedassignedgroupandfi;

    @ApiModelProperty(value = "PARSEDSUITEANDFI")
    private String parsedsuiteandfi;

    @ApiModelProperty(value = "PARSEDSUITEANDFILAB")
    private String parsedsuiteandfilab;

    @ApiModelProperty(value = "ID_GUIDENURSE")
    private String idGuidenurse;

    @ApiModelProperty(value = "PATIENTNAMEENCODED")
    private String patientnameencoded;

    @ApiModelProperty(value = "PATIENTCODEHIDEN")
    private String patientcodehiden;

    @ApiModelProperty(value = "F_WORDPRINTED")
    private String fWordprinted;

    @ApiModelProperty(value = "GUIDANCENOTE2")
    private String guidancenote2;

    @ApiModelProperty(value = "F_USECODEHIDEN")
    private String fUsecodehiden;

    @ApiModelProperty(value = "ID_PATIENTCLASS3")
    private String idPatientclass3;

    @ApiModelProperty(value = "DATEREGISTERNOTIME")
    private Date dateregisternotime;

    @ApiModelProperty(value = "COUNTERREPORTPRINTED")
    private String counterreportprinted;

    @ApiModelProperty(value = "0: 未复查 1: 复查")
    private String fIsrecheck;

    @ApiModelProperty(value = "F_SETTLENONE")
    private String fSettlenone;

    @ApiModelProperty(value = "DATEGUIDANCERETURNED")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "F_OUTPATIENT")
    private String fOutpatient;

    @ApiModelProperty(value = "PATIENTNAMERECEIPT")
    private String patientnamereceipt;

    @ApiModelProperty(value = "PATIENTNAMEPINYIN")
    private String patientnamepinyin;

    @ApiModelProperty(value = "STATUSOFHM")
    private String statusofhm;

    @ApiModelProperty(value = "INSTANCETAG")
    private String instancetag;

    @ApiModelProperty(value = "INPATIENTNO")
    private String inpatientno;

    @ApiModelProperty(value = "INSURANCENO")
    private String insuranceno;

    @ApiModelProperty(value = "COUNTREPORTXML")
    private String countreportxml;

    @ApiModelProperty(value = "COUNTREPORTCOMPARE")
    private String countreportcompare;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATION")
    private String countreportoccupation;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATIONPDF")
    private String countreportoccupationpdf;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATIONXML")
    private String countreportoccupationxml;

    @ApiModelProperty(value = "体检套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @ApiModelProperty(value = "记账人")
    private String jzdwr;

    @ApiModelProperty(value = "审批人")
    private String spr;

    @ApiModelProperty(value = "替检人")
    private String tjr;

    @ApiModelProperty(value = "领取方式")
    private String lqfs;

    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    @ApiModelProperty(value = "邮寄地址")
    private String yjaddress;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "黑名单备注")
    private String isHmdb;

    @ApiModelProperty(value = "黑名单")
    private String isHmd;

    @ApiModelProperty(value = "是否加急")
    private String isjj;

    @ApiModelProperty(value = "总工龄")
    private String zgl;

    @ApiModelProperty(value = "接害工龄")
    private String jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private String jktjzt;

    @ApiModelProperty(value = "0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private String zytjzt;

    @ApiModelProperty(value = "条码打印次数")
    private String tmyd;


    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "0:未回访 1：已回访")
    private String cjjgsfyhf;

    @ApiModelProperty(value = "0:未回访 1：已回访")
    private String bhgybsfyhf;

    @ApiModelProperty(value = "0:未回访 1：已回访")
    private String yxjgsfyhf;

    @ApiModelProperty(value = "上岗前 在岗期间 离岗时")
    private String medicaltype;

    @ApiModelProperty(value = "预付金额")
    private String prepayment;

    @ApiModelProperty(value = "X套餐价格")
    private String tcprice;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "0:小学 1:初中 2:技校 3:职高 4:高中 5:中专 6:大专 7:大学 8:研究生以上")
    private String cultural;

    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;

    @ApiModelProperty(value = "职业报告打印次数")
    private String diseasePrintNum;

    @ApiModelProperty(value = "健康报告打印次数")
    private String healthPrintNum;

    @ApiModelProperty(value = "分检完成时间")
    private Date readytofinalDate;

    @ApiModelProperty(value = "${column.comment}")
    private String guideSignleCount;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private String isNoticed;

    @ApiModelProperty(value = "复查PDF路径")
    private String reviewPdf;

    @ApiModelProperty(value = "职业禁忌证PDF路径")
    private String contraindicatedPdf;

    @ApiModelProperty(value = "疑似职业病PDF路径")
    private String diseasePdf;

    @ApiModelProperty(value = "${column.comment}")
    private String tsLimit;

    @ApiModelProperty(value = "${column.comment}")
    private Date checkoutDate;

    @ApiModelProperty(value = "${column.comment}")
    private String checkoutStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String physique;

    @ApiModelProperty(value = "${column.comment}")
    private String docName;

    @ApiModelProperty(value = "${column.comment}")
    private String worktypeId;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamclass;

    @ApiModelProperty(value = "${column.comment}")
    private String originalTrade;

    @ApiModelProperty(value = "${column.comment}")
    private String autoExamined;

    @ApiModelProperty(value = "${column.comment}")
    private String isNewPacs;

    @ApiModelProperty(value = "${column.comment}")
    private String committee;

    @ApiModelProperty(value = "${column.comment}")
    private String street;

    @ApiModelProperty(value = "${column.comment}")
    private String signPicture;
}
