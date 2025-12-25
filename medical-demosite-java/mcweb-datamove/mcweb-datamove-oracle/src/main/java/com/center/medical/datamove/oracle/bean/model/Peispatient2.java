package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * QT体检者表(Peispatient2)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:10
 */
@Data
@TableName("PEISPATIENT2")
@ApiModel(value = "Peispatient2", description = "QT体检者表实体类")
public class Peispatient2 extends Model<Peispatient2> implements Serializable {
    private static final long serialVersionUID = -91725154742818993L;

    @ApiModelProperty(value = "序号")
    private String idPatient;

    @ApiModelProperty(value = "预定体检编码")
    private Double idOrgpatient;

    @ApiModelProperty(value = "ID_CIS")
    private String idCis;

    @ApiModelProperty(value = "档案ID")
    private String idPatientarchive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检代码打印格式")
    private String patientcodeprn;

    @ApiModelProperty(value = "档案代码")
    private String patientarchiveno;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "业务编号")
    private String patientbizno;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "每日编码日期")
    private Date dailynumberdate;

    @ApiModelProperty(value = "每日编码")
    private Double dailynumber;

    @ApiModelProperty(value = "总编号")
    private Double numtotal;

    @ApiModelProperty(value = "年编号")
    private Double numyear;

    @ApiModelProperty(value = "月编号")
    private Double nummonth;

    @ApiModelProperty(value = "日编号")
    private Double numday;

    @ApiModelProperty(value = "团体编号")
    private Double numorg;

    @ApiModelProperty(value = "任务编号（订单号）")
    private Double numorgresv;

    @ApiModelProperty(value = "ID_PATIENTLINKED")
    private String idPatientlinked;

    @ApiModelProperty(value = "非团体虚拟团体ID")
    private Double idNonorg;

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

    @ApiModelProperty(value = "ORG_DEPARTSUBA")
    private String orgDepartsuba;

    @ApiModelProperty(value = "ORG_DEPARTSUBB")
    private String orgDepartsubb;

    @ApiModelProperty(value = "ORG_DEPARTSUBC")
    private String orgDepartsubc;

    @ApiModelProperty(value = "ORG_DEPARTSUBD")
    private String orgDepartsubd;

    @ApiModelProperty(value = "ORG_DEPARTSUBE")
    private String orgDepartsube;

    @ApiModelProperty(value = "是否有隐私项目：0.无 1.有")
    private String havePrivate;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "支付方式")
    private String payway;

    @ApiModelProperty(value = "扣率")
    private Double offpercent;

    @ApiModelProperty(value = "最大扣率")
    private Double maxoffpercent;

    @ApiModelProperty(value = "统收限额")
    private Double personpricelimit;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    private Double age;

    @ApiModelProperty(value = "年龄单位ID")
    private String idAgeunit;

    @ApiModelProperty(value = "年龄单位")
    private String ageunit;

    @ApiModelProperty(value = "实数年龄")
    private String ageofreal;

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

    @ApiModelProperty(value = "教育ID")
    private String idEducation;

    @ApiModelProperty(value = "教育程度")
    private String education;

    @ApiModelProperty(value = "职业ID")
    private String idOccupation;

    @ApiModelProperty(value = "职业")
    private String occupation;

    @ApiModelProperty(value = "地区ID")
    private String idResarea;

    @ApiModelProperty(value = "地区")
    private String resarea;

    @ApiModelProperty(value = "入职日期")
    private Date dateinorganization;

    @ApiModelProperty(value = "预定（已备单）")
    private Integer fIsforprepare;

    @ApiModelProperty(value = "预约")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "创建时间")
    private Date datecreated;

    @ApiModelProperty(value = "已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "位置")
    private String positionCode;

    @ApiModelProperty(value = "工作类型编码")
    private String jobtypeCode;

    @ApiModelProperty(value = "应付金额")
    private Double moneyamount;

    @ApiModelProperty(value = "实付金额")
    private Double moneyamountpaid;

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

    @ApiModelProperty(value = "体检类型")
    private String idExamsuite;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "EXAMSUITE_ALIAS")
    private String examsuiteAlias;

    @ApiModelProperty(value = "(不使用)")
    private Double idDoctorconclusion;

    @ApiModelProperty(value = "(不使用)2")
    private String doctorconclusion;

    @ApiModelProperty(value = "开单医生ID2总检锁定人")
    private String idDoctorapply;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "指引单已打")
    private Integer fGuidanceprinted;

    @ApiModelProperty(value = "已缴费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "收费员ID")
    private String idDoctorfee;

    @ApiModelProperty(value = "收费员")
    private String doctorfee;

    @ApiModelProperty(value = "禁检")
    private Integer fPaused;

    @ApiModelProperty(value = "总检锁定")
    private Integer fFinallocked;

    @ApiModelProperty(value = "总检完成")
    private Integer fFinalexamed;

    @ApiModelProperty(value = "总审完成")
    private Integer fFinalapproved;

    @ApiModelProperty(value = "总检医生ID")
    private String idDoctorfinal;

    @ApiModelProperty(value = "总检医生")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "总检时间")
    private Date datefinalexamed;

    @ApiModelProperty(value = "总审医生")
    private String idDoctorfinalapproved;

    @ApiModelProperty(value = "总审时间")
    private Date datefinalapproved;

    @ApiModelProperty(value = "磁卡已发")
    private Integer fCardissued;

    @ApiModelProperty(value = "磁卡已交还")
    private Integer fCardreturned;

    @ApiModelProperty(value = "报告封面已打印")
    private Integer fCoverprinted;

    @ApiModelProperty(value = "报告已打印")
    private Integer fReportprinted;

    @ApiModelProperty(value = "报告打印者ID")
    private String idReportprintedby;

    @ApiModelProperty(value = "报告打印日期")
    private Date datereportprinted;

    @ApiModelProperty(value = "报告领取通知")
    private Integer fReportinformed;

    @ApiModelProperty(value = "报告领取通知时间")
    private Date datereportinformed;

    @ApiModelProperty(value = "报告已取")
    private Integer fReportfetched;

    @ApiModelProperty(value = "报告领取日期")
    private Date datereportfetched;

    @ApiModelProperty(value = "重大疾病")
    private Integer fIssevere;

    @ApiModelProperty(value = "结案")
    private Integer fClosed;

    @ApiModelProperty(value = "结案日期")
    private Date dateclosed;

    @ApiModelProperty(value = "需要跟踪")
    private Integer fNeedtraced;

    @ApiModelProperty(value = "非本人体检(是否替检)")
    private Integer fDiffperson;

    @ApiModelProperty(value = "密级")
    private Double confidentiallevel;

    @ApiModelProperty(value = "预结")
    private Integer fSettleall;

    @ApiModelProperty(value = "签名")
    private String signature;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "最近修改时间")
    private Date dtLastmodifiedthisat;

    @ApiModelProperty(value = "内部操作标志")
    private Integer fInneroper;

    @ApiModelProperty(value = "重症级别")
    private Double severedegree;

    @ApiModelProperty(value = "重症说明")
    private String severedegreenote;

    @ApiModelProperty(value = "重症已通知")
    private Integer fSevereinformed;

    @ApiModelProperty(value = "重症通知时间")
    private Date severeinformtime;

    @ApiModelProperty(value = "重症通知人ID")
    private String idSevereinformby;

    @ApiModelProperty(value = "综述")
    private String conclusion;

    @ApiModelProperty(value = "结论")
    private String conclusionsummary;

    @ApiModelProperty(value = "建议")
    private String suggestion;

    @ApiModelProperty(value = "建议(暂不使用)")
    private String conclusionrich;

    @ApiModelProperty(value = "饮食建议")
    private String dietguide;

    @ApiModelProperty(value = "运行建议")
    private String sportguide;

    @ApiModelProperty(value = "健康知识")
    private String knowledge;

    @ApiModelProperty(value = "温馨提示")
    private String message;

    @ApiModelProperty(value = "阳性综述")
    private String positivesummary;

    @ApiModelProperty(value = "结果比较")
    private String resultcompare;

    @ApiModelProperty(value = "接口标志")
    private String interfacemarks;

    @ApiModelProperty(value = "体检者标志")
    private String patientflag;

    @ApiModelProperty(value = "体检者记时器开始")
    private Date timingstartedat;

    @ApiModelProperty(value = "体检者记时器改变时间")
    private Date timeresultlastchange;

    @ApiModelProperty(value = "体检者结果上次档案")
    private Date timeresultlastarchive;

    @ApiModelProperty(value = "体检者结果上一次OLAP时间")
    private Date timeresultlastolap;

    @ApiModelProperty(value = "医院代码")
    private String hospitalcode;

    @ApiModelProperty(value = "医院名称")
    private String hospitalname;

    @ApiModelProperty(value = "ID_EXAMPLACE")
    private Double idExamplace;

    @ApiModelProperty(value = "PARSEDASSIGNEDSUITEANDFI")
    private String parsedassignedsuiteandfi;

    @ApiModelProperty(value = "PARSEDASSIGNEDGROUPANDFI")
    private String parsedassignedgroupandfi;

    @ApiModelProperty(value = "PARSEDSUITEANDFI")
    private String parsedsuiteandfi;

    @ApiModelProperty(value = "PARSEDSUITEANDFILAB")
    private String parsedsuiteandfilab;

    @ApiModelProperty(value = "职业锁定")
    private Double idGuidenurse;

    @ApiModelProperty(value = "PATIENTNAMEENCODED")
    private String patientnameencoded;

    @ApiModelProperty(value = "PATIENTCODEHIDEN")
    private String patientcodehiden;

    @ApiModelProperty(value = "F_PDFCREATED")
    private Integer fPdfcreated;

    @ApiModelProperty(value = "F_WORDCREATED")
    private Integer fWordcreated;

    @ApiModelProperty(value = "F_WORDPRINTED")
    private Integer fWordprinted;

    @ApiModelProperty(value = "GUIDANCENOTE2")
    private String guidancenote2;

    @ApiModelProperty(value = "F_USECODEHIDEN")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "ID_PATIENTCLASS2")
    private Double idPatientclass2;

    @ApiModelProperty(value = "ID_PATIENTCLASS3")
    private Double idPatientclass3;

    @ApiModelProperty(value = "DATEREGISTERNOTIME")
    private Date dateregisternotime;

    @ApiModelProperty(value = "COUNTERREPORTPRINTED")
    private Double counterreportprinted;

    @ApiModelProperty(value = "F_PRINTCOMPARINGREPORT")
    private Integer fPrintcomparingreport;

    @ApiModelProperty(value = "0: 未复查 1: 复查")
    private Integer fIsrecheck;

    @ApiModelProperty(value = "F_SETTLENONE")
    private Integer fSettlenone;

    @ApiModelProperty(value = "F_GUIDANCERETURNED")
    private Integer fGuidancereturned;

    @ApiModelProperty(value = "DATEGUIDANCERETURNED")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "ID_GUIDANCERETURNEDBY")
    private Double idGuidancereturnedby;

    @ApiModelProperty(value = "F_OUTPATIENT")
    private Integer fOutpatient;

    @ApiModelProperty(value = "PATIENTNAMERECEIPT")
    private String patientnamereceipt;

    @ApiModelProperty(value = "PATIENTNAMEPINYIN")
    private String patientnamepinyin;

    @ApiModelProperty(value = "F_FORPREPAREFINANCIALCONFIRM")
    private Integer fForpreparefinancialconfirm;

    @ApiModelProperty(value = "STATUSOFHM")
    private String statusofhm;

    @ApiModelProperty(value = "INSTANCETAG")
    private String instancetag;

    @ApiModelProperty(value = "KEYBIRTHPLACE")
    private String keybirthplace;

    @ApiModelProperty(value = "KEYBLOODTYPE")
    private String keybloodtype;

    @ApiModelProperty(value = "EXAMMETHOD")
    private String exammethod;

    @ApiModelProperty(value = "INPATIENTNO")
    private String inpatientno;

    @ApiModelProperty(value = "INSURANCENO")
    private String insuranceno;

    @ApiModelProperty(value = "KEYPAYWAY")
    private String keypayway;

    @ApiModelProperty(value = "HEALTHCARD")
    private String healthcard;

    @ApiModelProperty(value = "EXAMPOINT")
    private String exampoint;

    @ApiModelProperty(value = "FINGERPRINT")
    private String fingerprint;

    @ApiModelProperty(value = "COUNTREPORTCOVERPRINTED")
    private Double countreportcoverprinted;

    @ApiModelProperty(value = "COUNTREPORTPRINTED")
    private Double countreportprinted;

    @ApiModelProperty(value = "COUNTREPORTPDF")
    private Double countreportpdf;

    @ApiModelProperty(value = "COUNTREPORTWORD")
    private Double countreportword;

    @ApiModelProperty(value = "COUNTREPORTXML")
    private Double countreportxml;

    @ApiModelProperty(value = "COUNTREPORTCOMPARE")
    private Double countreportcompare;

    @ApiModelProperty(value = "COUNTREPORTCOMPAREPDF")
    private Double countreportcomparepdf;

    @ApiModelProperty(value = "COUNTREPORTCOMPAREWORD")
    private Double countreportcompareword;

    @ApiModelProperty(value = "COUNTREPORTCOMPAREXML")
    private Double countreportcomparexml;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATION")
    private Double countreportoccupation;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATIONPDF")
    private Double countreportoccupationpdf;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATIONWORD")
    private Double countreportoccupationword;

    @ApiModelProperty(value = "COUNTREPORTOCCUPATIONXML")
    private Double countreportoccupationxml;

    @ApiModelProperty(value = "上传标示")
    private Integer scbs;

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
    private Integer isHmd;

    @ApiModelProperty(value = "是否加急")
    private Integer isjj;

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

    @ApiModelProperty(value = "XID")
    private String id;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "0:未回访 1：已回访")
    private Integer cjjgsfyhf;

    @ApiModelProperty(value = "0:未回访 1：已回访")
    private Integer bhgybsfyhf;

    @ApiModelProperty(value = "0:未回访 1：已回访")
    private Integer yxjgsfyhf;

    @ApiModelProperty(value = "0:上岗前 1:在岗期间 2:离岗时")
    private String medicaltype;

    @ApiModelProperty(value = "预付金额")
    private Double prepayment;

    @ApiModelProperty(value = "X套餐价格")
    private Double tcprice;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "0:小学 1:初中 2:技校 3:职高 4:高中 5:中专 6:大专 7:大学 8:研究生以上 ")
    private Integer cultural;

    @ApiModelProperty(value = "既往病ids")
    private String everOfDisease;

    @ApiModelProperty(value = "初潮年龄")
    private String ccnl;

    @ApiModelProperty(value = "经期")
    private String jq;

    @ApiModelProperty(value = "周期")
    private String zq;

    @ApiModelProperty(value = "停经年龄")
    private String tjnl;

    @ApiModelProperty(value = "现有子女人数")
    private String familyNumber;

    @ApiModelProperty(value = "早产")
    private String zc;

    @ApiModelProperty(value = "死产")
    private String sc;

    @ApiModelProperty(value = "流产")
    private String lc;

    @ApiModelProperty(value = "畸胎")
    private String jt;

    @ApiModelProperty(value = "异位妊娠")
    private String ywrc;

    @ApiModelProperty(value = "吸烟情况")
    private String abstainSmokeNote;

    @ApiModelProperty(value = "每天吸烟支数")
    private String everydaySmokeN;

    @ApiModelProperty(value = "吸烟年数")
    private String smokeYear;

    @ApiModelProperty(value = "是否不饮酒")
    private String noKissTheCup;

    @ApiModelProperty(value = "是否偶饮酒")
    private String betweenKissTheCup;

    @ApiModelProperty(value = "是否经常饮酒")
    private String evermoreKiss;

    @ApiModelProperty(value = "是否戒酒")
    private String abstainLostKiss;

    @ApiModelProperty(value = "经常饮酒年数")
    private String kissYearN;

    @ApiModelProperty(value = "饮酒量")
    private String kissAmount;

    @ApiModelProperty(value = "饮酒种类")
    private String kissType;

    @ApiModelProperty(value = "家族病史")
    private String familyOfDisease;

    @ApiModelProperty(value = "症状")
    private String symptom;

    @ApiModelProperty(value = "是否已审核（职业性问诊）")
    private Integer isAudit;

    @ApiModelProperty(value = "既往病备注")
    private String everOfDiseaseRemark;

    @ApiModelProperty(value = "生成次数")
    private String createReportNum;

    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;

    @ApiModelProperty(value = "照片")
    private String picture;

    @ApiModelProperty(value = "健康建议")
    private String advice;

    @ApiModelProperty(value = "职业报告打印次数")
    private String diseasePrintNum;

    @ApiModelProperty(value = "健康报告打印次数")
    private String healthPrintNum;

    @ApiModelProperty(value = "分检完成时间")
    private Date readytofinalDate;

    @ApiModelProperty(value = "${column.comment}")
    private Double guideSignleCount;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isNoticed;

    @ApiModelProperty(value = "复查PDF路径")
    private String reviewPdf;

    @ApiModelProperty(value = "职业禁忌证PDF路径")
    private String contraindicatedPdf;

    @ApiModelProperty(value = "疑似职业病PDF路径")
    private String diseasePdf;

    @ApiModelProperty(value = "${column.comment}")
    private String signPicture;

    @ApiModelProperty(value = "${column.comment}")
    private Double tsLimit;

    @ApiModelProperty(value = "${column.comment}")
    private String committee;

    @ApiModelProperty(value = "${column.comment}")
    private String street;

    @ApiModelProperty(value = "${column.comment}")
    private Date checkoutDate;

    @ApiModelProperty(value = "${column.comment}")
    private Double checkoutStatus;
}
