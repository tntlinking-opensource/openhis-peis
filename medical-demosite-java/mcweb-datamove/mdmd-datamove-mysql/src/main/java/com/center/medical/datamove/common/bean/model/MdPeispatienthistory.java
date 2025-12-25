package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者（history）表(MdPeispatienthistory)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
@Data
@TableName("md_peispatienthistory")
@ApiModel(value = "MdPeispatienthistory", description = "体检者（history）表实体类")
public class MdPeispatienthistory extends Model<MdPeispatienthistory> implements Serializable {
    private static final long serialVersionUID = -68303541195859484L;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatient;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idOrgpatient;

    @ApiModelProperty(value = "${column.comment}")
    private String idCis;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientarchive;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcodeprn;

    @ApiModelProperty(value = "${column.comment}")
    private String patientarchiveno;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcardno;

    @ApiModelProperty(value = "${column.comment}")
    private String patientbizno;

    @ApiModelProperty(value = "${column.comment}")
    private String idcardno;

    @ApiModelProperty(value = "${column.comment}")
    private Date dailynumberdate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer dailynumber;

    @ApiModelProperty(value = "${column.comment}")
    private Integer numtotal;

    @ApiModelProperty(value = "${column.comment}")
    private Integer numyear;

    @ApiModelProperty(value = "${column.comment}")
    private Integer nummonth;

    @ApiModelProperty(value = "${column.comment}")
    private Integer numday;

    @ApiModelProperty(value = "${column.comment}")
    private Integer numorg;

    @ApiModelProperty(value = "${column.comment}")
    private Integer numorgresv;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientlinked;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idNonorg;

    @ApiModelProperty(value = "${column.comment}")
    private String patientname;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "${column.comment}")
    private String idOrgreservation;

    @ApiModelProperty(value = "${column.comment}")
    private String idOrg;

    @ApiModelProperty(value = "${column.comment}")
    private String orgName;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepart;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepartsuba;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepartsubb;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepartsubc;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepartsubd;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepartsube;

    @ApiModelProperty(value = "${column.comment}")
    private String havePrivate;

    @ApiModelProperty(value = "${column.comment}")
    private String idPayway;

    @ApiModelProperty(value = "${column.comment}")
    private String payway;

    @ApiModelProperty(value = "${column.comment}")
    private Double offpercent;

    @ApiModelProperty(value = "${column.comment}")
    private Double maxoffpercent;

    @ApiModelProperty(value = "${column.comment}")
    private Integer personpricelimit;

    @ApiModelProperty(value = "${column.comment}")
    private String idSex;

    @ApiModelProperty(value = "${column.comment}")
    private String sex;

    @ApiModelProperty(value = "${column.comment}")
    private Date birthdate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer age;

    @ApiModelProperty(value = "${column.comment}")
    private String idAgeunit;

    @ApiModelProperty(value = "${column.comment}")
    private String ageunit;

    @ApiModelProperty(value = "${column.comment}")
    private Integer ageofreal;

    @ApiModelProperty(value = "${column.comment}")
    private String idMarriage;

    @ApiModelProperty(value = "${column.comment}")
    private String marriage;

    @ApiModelProperty(value = "${column.comment}")
    private String idNation;

    @ApiModelProperty(value = "${column.comment}")
    private String nation;

    @ApiModelProperty(value = "${column.comment}")
    private String address;

    @ApiModelProperty(value = "${column.comment}")
    private String idInformway;

    @ApiModelProperty(value = "${column.comment}")
    private String idOpendoctor;

    @ApiModelProperty(value = "${column.comment}")
    private String email;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientclass;

    @ApiModelProperty(value = "${column.comment}")
    private String idEducation;

    @ApiModelProperty(value = "${column.comment}")
    private String education;

    @ApiModelProperty(value = "${column.comment}")
    private String idOccupation;

    @ApiModelProperty(value = "${column.comment}")
    private String occupation;

    @ApiModelProperty(value = "${column.comment}")
    private String idResarea;

    @ApiModelProperty(value = "${column.comment}")
    private String resarea;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateinorganization;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIsforprepare;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "${column.comment}")
    private Date datecreated;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fRegistered;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregister;

    @ApiModelProperty(value = "${column.comment}")
    private String positionCode;

    @ApiModelProperty(value = "${column.comment}")
    private String jobtypeCode;

    @ApiModelProperty(value = "${column.comment}")
    private Double moneyamount;

    @ApiModelProperty(value = "${column.comment}")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "${column.comment}")
    private String guidancenote;

    @ApiModelProperty(value = "${column.comment}")
    private String workno;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorreg;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorreg;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamtype;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamsuite;

    @ApiModelProperty(value = "${column.comment}")
    private String examsuiteName;

    @ApiModelProperty(value = "${column.comment}")
    private String examsuiteAlias;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idDoctorconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorapply;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorapply;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fGuidanceprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFeecharged;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fExamstarted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorfee;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorfee;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fPaused;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFinallocked;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFinalexamed;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFinalapproved;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorfinal;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "${column.comment}")
    private Date datefinalexamed;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorfinalapproved;

    @ApiModelProperty(value = "${column.comment}")
    private Date datefinalapproved;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fCardissued;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fCardreturned;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fCoverprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fReportprinted;

    @ApiModelProperty(value = "${column.comment}")
    private String idReportprintedby;

    @ApiModelProperty(value = "${column.comment}")
    private Date datereportprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fReportinformed;

    @ApiModelProperty(value = "${column.comment}")
    private Date datereportinformed;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fReportfetched;

    @ApiModelProperty(value = "${column.comment}")
    private Date datereportfetched;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIssevere;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fClosed;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateclosed;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fNeedtraced;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fDiffperson;

    @ApiModelProperty(value = "${column.comment}")
    private Integer confidentiallevel;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fSettleall;

    @ApiModelProperty(value = "${column.comment}")
    private String signature;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Date dtLastmodifiedthisat;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fInneroper;

    @ApiModelProperty(value = "${column.comment}")
    private Integer severedegree;

    @ApiModelProperty(value = "${column.comment}")
    private String severedegreenote;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fSevereinformed;

    @ApiModelProperty(value = "${column.comment}")
    private Date severeinformtime;

    @ApiModelProperty(value = "${column.comment}")
    private String idSevereinformby;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionsummary;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestion;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionrich;

    @ApiModelProperty(value = "${column.comment}")
    private String dietguide;

    @ApiModelProperty(value = "${column.comment}")
    private String sportguide;

    @ApiModelProperty(value = "${column.comment}")
    private String knowledge;

    @ApiModelProperty(value = "${column.comment}")
    private String message;

    @ApiModelProperty(value = "${column.comment}")
    private String positivesummary;

    @ApiModelProperty(value = "${column.comment}")
    private String resultcompare;

    @ApiModelProperty(value = "${column.comment}")
    private String interfacemarks;

    @ApiModelProperty(value = "${column.comment}")
    private String patientflag;

    @ApiModelProperty(value = "${column.comment}")
    private Date timingstartedat;

    @ApiModelProperty(value = "${column.comment}")
    private Date timeresultlastchange;

    @ApiModelProperty(value = "${column.comment}")
    private Date timeresultlastarchive;

    @ApiModelProperty(value = "${column.comment}")
    private Date timeresultlastolap;

    @ApiModelProperty(value = "${column.comment}")
    private String hospitalcode;

    @ApiModelProperty(value = "${column.comment}")
    private String hospitalname;

    @ApiModelProperty(value = "${column.comment}")
    private Double idExamplace;

    @ApiModelProperty(value = "${column.comment}")
    private String parsedassignedsuiteandfi;

    @ApiModelProperty(value = "${column.comment}")
    private String parsedassignedgroupandfi;

    @ApiModelProperty(value = "${column.comment}")
    private String parsedsuiteandfi;

    @ApiModelProperty(value = "${column.comment}")
    private String parsedsuiteandfilab;

    @ApiModelProperty(value = "${column.comment}")
    private Double idGuidenurse;

    @ApiModelProperty(value = "${column.comment}")
    private String patientnameencoded;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcodehiden;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fPdfcreated;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fWordcreated;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fWordprinted;

    @ApiModelProperty(value = "${column.comment}")
    private String guidancenote2;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idPatientclass2;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idPatientclass3;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregisternotime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer counterreportprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fPrintcomparingreport;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIsrecheck;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fSettlenone;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fGuidancereturned;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "${column.comment}")
    private Double idGuidancereturnedby;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fOutpatient;

    @ApiModelProperty(value = "${column.comment}")
    private String patientnamereceipt;

    @ApiModelProperty(value = "${column.comment}")
    private String patientnamepinyin;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fForpreparefinancialconfirm;

    @ApiModelProperty(value = "${column.comment}")
    private String statusofhm;

    @ApiModelProperty(value = "${column.comment}")
    private String instancetag;

    @ApiModelProperty(value = "${column.comment}")
    private String keybirthplace;

    @ApiModelProperty(value = "${column.comment}")
    private String keybloodtype;

    @ApiModelProperty(value = "${column.comment}")
    private String exammethod;

    @ApiModelProperty(value = "${column.comment}")
    private String inpatientno;

    @ApiModelProperty(value = "${column.comment}")
    private String insuranceno;

    @ApiModelProperty(value = "${column.comment}")
    private String keypayway;

    @ApiModelProperty(value = "${column.comment}")
    private String healthcard;

    @ApiModelProperty(value = "${column.comment}")
    private String exampoint;

    @ApiModelProperty(value = "${column.comment}")
    private String fingerprint;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportcoverprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportpdf;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportword;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportxml;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportcompare;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportcomparepdf;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportcompareword;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportcomparexml;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportoccupation;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportoccupationpdf;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportoccupationword;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "${column.comment}")
    private Integer scbs;

    @ApiModelProperty(value = "${column.comment}")
    private String idTjtc;

    @ApiModelProperty(value = "${column.comment}")
    private String jzdw;

    @ApiModelProperty(value = "${column.comment}")
    private String jzdwr;

    @ApiModelProperty(value = "${column.comment}")
    private String spr;

    @ApiModelProperty(value = "${column.comment}")
    private String tjr;

    @ApiModelProperty(value = "${column.comment}")
    private String lqfs;

    @ApiModelProperty(value = "${column.comment}")
    private Object yzbm;

    @ApiModelProperty(value = "${column.comment}")
    private String yjaddress;

    @ApiModelProperty(value = "${column.comment}")
    private String qtxz;

    @ApiModelProperty(value = "${column.comment}")
    private String isHmdb;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isHmd;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isjj;

    @ApiModelProperty(value = "${column.comment}")
    private Integer zgl;

    @ApiModelProperty(value = "${column.comment}")
    private Integer jhgl;

    @ApiModelProperty(value = "${column.comment}")
    private String jhys;

    @ApiModelProperty(value = "${column.comment}")
    private Integer jktjzt;

    @ApiModelProperty(value = "${column.comment}")
    private Integer zytjzt;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tmyd;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date medicaldate;

    @ApiModelProperty(value = "${column.comment}")
    private String trades;

    @ApiModelProperty(value = "${column.comment}")
    private Integer cjjgsfyhf;

    @ApiModelProperty(value = "${column.comment}")
    private Integer bhgybsfyhf;

    @ApiModelProperty(value = "${column.comment}")
    private Integer yxjgsfyhf;

    @ApiModelProperty(value = "${column.comment}")
    private String medicaltype;

    @ApiModelProperty(value = "${column.comment}")
    private Double prepayment;

    @ApiModelProperty(value = "${column.comment}")
    private Double tcprice;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer cultural;

    @ApiModelProperty(value = "${column.comment}")
    private String everOfDisease;

    @ApiModelProperty(value = "${column.comment}")
    private String ccnl;

    @ApiModelProperty(value = "${column.comment}")
    private String jq;

    @ApiModelProperty(value = "${column.comment}")
    private String zq;

    @ApiModelProperty(value = "${column.comment}")
    private String tjnl;

    @ApiModelProperty(value = "${column.comment}")
    private String familyNumber;

    @ApiModelProperty(value = "${column.comment}")
    private String zc;

    @ApiModelProperty(value = "${column.comment}")
    private String sc;

    @ApiModelProperty(value = "${column.comment}")
    private String lc;

    @ApiModelProperty(value = "${column.comment}")
    private String jt;

    @ApiModelProperty(value = "${column.comment}")
    private String ywrc;

    @ApiModelProperty(value = "${column.comment}")
    private String abstainSmokeNote;

    @ApiModelProperty(value = "${column.comment}")
    private String everydaySmokeN;

    @ApiModelProperty(value = "${column.comment}")
    private String smokeYear;

    @ApiModelProperty(value = "${column.comment}")
    private String noKissTheCup;

    @ApiModelProperty(value = "${column.comment}")
    private String betweenKissTheCup;

    @ApiModelProperty(value = "${column.comment}")
    private String evermoreKiss;

    @ApiModelProperty(value = "${column.comment}")
    private String abstainLostKiss;

    @ApiModelProperty(value = "${column.comment}")
    private String kissYearN;

    @ApiModelProperty(value = "${column.comment}")
    private String kissAmount;

    @ApiModelProperty(value = "${column.comment}")
    private String kissType;

    @ApiModelProperty(value = "${column.comment}")
    private String familyOfDisease;

    @ApiModelProperty(value = "${column.comment}")
    private String symptom;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isAudit;

    @ApiModelProperty(value = "${column.comment}")
    private String everOfDiseaseRemark;

    @ApiModelProperty(value = "${column.comment}")
    private Integer createReportNum;

    @ApiModelProperty(value = "${column.comment}")
    private Date workDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date harmDate;

    @ApiModelProperty(value = "${column.comment}")
    private String picture;

    @ApiModelProperty(value = "${column.comment}")
    private String advice;

    @ApiModelProperty(value = "${column.comment}")
    private Integer diseasePrintNum;

    @ApiModelProperty(value = "${column.comment}")
    private Integer healthPrintNum;

    @ApiModelProperty(value = "${column.comment}")
    private Date readytofinalDate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer guideSignleCount;

    @ApiModelProperty(value = "${column.comment}")
    private Integer shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isNoticed;

    @ApiModelProperty(value = "${column.comment}")
    private String reviewPdf;

    @ApiModelProperty(value = "${column.comment}")
    private String contraindicatedPdf;

    @ApiModelProperty(value = "${column.comment}")
    private String diseasePdf;

    @ApiModelProperty(value = "${column.comment}")
    private String signPicture;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isNewPacs;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tsLimit;

    @ApiModelProperty(value = "${column.comment}")
    private String committee;

    @ApiModelProperty(value = "${column.comment}")
    private String street;

    @ApiModelProperty(value = "${column.comment}")
    private Date checkoutDate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer checkoutStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String physique;

    @ApiModelProperty(value = "${column.comment}")
    private String docName;

    @ApiModelProperty(value = "${column.comment}")
    private String worktypeId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idExamclass;
}
