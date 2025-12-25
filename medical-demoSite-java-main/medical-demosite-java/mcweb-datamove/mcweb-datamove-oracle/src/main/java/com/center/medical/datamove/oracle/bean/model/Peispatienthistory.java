package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Peispatienthistory)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:30
 */
@Data
@TableName("PEISPATIENTHISTORY")
@ApiModel(value = "Peispatienthistory", description = "$tableInfo.comment实体类")
public class Peispatienthistory extends Model<Peispatienthistory> implements Serializable {
    private static final long serialVersionUID = -47443033194379717L;

    @ApiModelProperty(value = "${column.comment}")
    private String idCis;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientarchive;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String patientarchiveno;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcardno;

    @ApiModelProperty(value = "${column.comment}")
    private String patientbizno;

    @ApiModelProperty(value = "${column.comment}")
    private String idcardno;

    @ApiModelProperty(value = "${column.comment}")
    private Double numorgresv;

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
    private String havePrivate;

    @ApiModelProperty(value = "${column.comment}")
    private String idPayway;

    @ApiModelProperty(value = "${column.comment}")
    private String payway;

    @ApiModelProperty(value = "${column.comment}")
    private Double personpricelimit;

    @ApiModelProperty(value = "${column.comment}")
    private String idSex;

    @ApiModelProperty(value = "${column.comment}")
    private Date birthdate;

    @ApiModelProperty(value = "${column.comment}")
    private Double age;

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
    private String idResarea;

    @ApiModelProperty(value = "${column.comment}")
    private String resarea;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIsforprepare;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIsforreserve;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fRegistered;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregister;

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
    private String examsuiteName;

    @ApiModelProperty(value = "${column.comment}")
    private String examsuiteAlias;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorapply;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorapply;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fGuidanceprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fExamstarted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorfee;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fPaused;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFinallocked;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fFinalexamed;

    @ApiModelProperty(value = "${column.comment}")
    private String idDoctorfinal;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorfinalNameR;

    @ApiModelProperty(value = "${column.comment}")
    private Date datefinalexamed;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIssevere;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fClosed;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateclosed;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fSevereinformed;

    @ApiModelProperty(value = "${column.comment}")
    private String knowledge;

    @ApiModelProperty(value = "${column.comment}")
    private Date timingstartedat;

    @ApiModelProperty(value = "${column.comment}")
    private String hospitalcode;

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
    private Integer fWordprinted;

    @ApiModelProperty(value = "${column.comment}")
    private String guidancenote2;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "${column.comment}")
    private Double idPatientclass3;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregisternotime;

    @ApiModelProperty(value = "${column.comment}")
    private Double counterreportprinted;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fIsrecheck;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fSettlenone;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fOutpatient;

    @ApiModelProperty(value = "${column.comment}")
    private String patientnamereceipt;

    @ApiModelProperty(value = "${column.comment}")
    private String patientnamepinyin;

    @ApiModelProperty(value = "${column.comment}")
    private String statusofhm;

    @ApiModelProperty(value = "${column.comment}")
    private String instancetag;

    @ApiModelProperty(value = "${column.comment}")
    private String inpatientno;

    @ApiModelProperty(value = "${column.comment}")
    private String insuranceno;

    @ApiModelProperty(value = "${column.comment}")
    private Double countreportxml;

    @ApiModelProperty(value = "${column.comment}")
    private Double countreportcompare;

    @ApiModelProperty(value = "${column.comment}")
    private Double countreportoccupation;

    @ApiModelProperty(value = "${column.comment}")
    private Double countreportoccupationpdf;

    @ApiModelProperty(value = "${column.comment}")
    private Double countreportoccupationxml;

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
    private String yzbm;

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
    private String zgl;

    @ApiModelProperty(value = "${column.comment}")
    private String jhgl;

    @ApiModelProperty(value = "${column.comment}")
    private String jhys;

    @ApiModelProperty(value = "${column.comment}")
    private String jktjzt;

    @ApiModelProperty(value = "${column.comment}")
    private String zytjzt;

    @ApiModelProperty(value = "${column.comment}")
    private String tmyd;

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
    private Date workDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date harmDate;

    @ApiModelProperty(value = "${column.comment}")
    private String diseasePrintNum;

    @ApiModelProperty(value = "${column.comment}")
    private String healthPrintNum;

    @ApiModelProperty(value = "${column.comment}")
    private Date readytofinalDate;

    @ApiModelProperty(value = "${column.comment}")
    private Double guideSignleCount;

    @ApiModelProperty(value = "${column.comment}")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isNoticed;

    @ApiModelProperty(value = "${column.comment}")
    private String reviewPdf;

    @ApiModelProperty(value = "${column.comment}")
    private String contraindicatedPdf;

    @ApiModelProperty(value = "${column.comment}")
    private String diseasePdf;

    @ApiModelProperty(value = "${column.comment}")
    private Double tsLimit;

    @ApiModelProperty(value = "${column.comment}")
    private Date checkoutDate;

    @ApiModelProperty(value = "${column.comment}")
    private Double checkoutStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String physique;

    @ApiModelProperty(value = "${column.comment}")
    private String docName;
}
