package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MD_PEISPATIENT")
public class Peispatient {
    private String idCis;
    private String patientcode;
    private String patientarchiveno;
    private String patientbizno;
    private String idcardno;
    private String numorgresv;
    private String patientname;
    private String inputCode;
    private String idOrgreservationgroup;
    private String idOrgreservation;
    private String idOrg;
    private String orgName;
    private String orgDepart;
    private String havePrivate;
    private String idPayway;
    private String payway;
    private Double personpricelimit;
    private String idSex;
    private Date birthdate;
    private Integer age;
    private String idMarriage;
    private String marriage;
    private String idNation;
    private String nation;
    private String address;
    private String idInformway;
    private String idOpendoctor;
    private String email;
    private String phone;
    private String idPatientclass;
    private String idResarea;
    private String resarea;
    private Integer fIsforprepare;
    private Integer fIsforreserve;
    private Integer fRegistered;
    private Date dateregister;
    private Double moneyamount;
    private Double moneyamountpaid;
    private String guidancenote;
    private String workno;
    private String idDoctorreg;
    private String doctorreg;
    private String idExamtype;
    private String examsuiteName;
    private String examsuiteAlias;
    private String idDoctorapply;
    private String doctorapply;
    private Integer fGuidanceprinted;
    private Integer fExamstarted;
    private Integer fReadytofinal;
    private Integer fPaused;
    private Integer fFinallocked;
    private Integer fFinalexamed;
    private String idDoctorfinal;
    private String doctorfinalNameR;
    private Date datefinalexamed;
    private Integer fClosed;
    private Date dateclosed;
    private String note;
    private String knowledge;
    private Date timingstartedat;
    private String hospitalcode;
    private Integer idExamplace;
    private String parsedassignedsuiteandfi;
    private String parsedassignedgroupandfi;
    private String parsedsuiteandfi;
    private String parsedsuiteandfilab;
    private Integer idGuidenurse;
    private String patientnameencoded;
    private String patientcodehiden;
    private Integer fWordprinted;
    private String guidancenote2;
    private Integer fUsecodehiden;
    private Integer idPatientclass3;
    private Date dateregisternotime;
    private Integer counterreportprinted;
    private Integer fIsrecheck;
    private Integer fSettlenone;
    private Date dateguidancereturned;
    private Integer fOutpatient;
    private String patientnamereceipt;
    private String patientnamepinyin;
    private String statusofhm;
    private String instancetag;
    private String inpatientno;
    private String insuranceno;
    private Integer countreportxml;
    private Integer countreportcompare;
    private Integer countreportoccupation;
    private Integer countreportoccupationpdf;
    private Integer countreportoccupationxml;
    private String idTjtc;
    private String jzdw;
    private String jzdwr;
    private String spr;
    private String tjr;
    private String lqfs;
    private String yzbm;
    private String yjaddress;
    private String qtxz;
    private String isHmdb;
    private Integer isjj;
    private Long zgl;
    private Long jhgl;
    private String jhys;
    private Long jktjzt;
    private Long zytjzt;
    private Long tmyd;
    private String id;
    private Date medicaldate;
    private String trades;
    private Integer cjjgsfyhf;
    private Integer bhgybsfyhf;
    private Integer yxjgsfyhf;
    private String medicaltype;
    private Long prepayment;
    private Long tcprice;
    private Date createdate;
    private Date modifydate;
    private Integer cultural;
    private Date workDate;
    private Date harmDate;
    private Integer diseasePrintNum;
    private Integer healthPrintNum;
    private Date readytofinalDate;
    private Integer guideSignleCount;
    private Integer shortCode;
    private String reviewPdf;
    private String contraindicatedPdf;
    private String diseasePdf;
    private Double tsLimit;
    private Date checkoutDate;
    private Integer checkoutStatus;
    private String worktypeId;

    @Basic
    @Column(name = "ID_CIS", nullable = true, length = 50)
    public String getIdCis() {
        return idCis;
    }

    public void setIdCis(String idCis) {
        this.idCis = idCis;
    }

    @Basic
    @Column(name = "PATIENTCODE", nullable = true, length = 50)
    public String getPatientcode() {
        return patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }

    @Basic
    @Column(name = "PATIENTARCHIVENO", nullable = true, length = 30)
    public String getPatientarchiveno() {
        return patientarchiveno;
    }

    public void setPatientarchiveno(String patientarchiveno) {
        this.patientarchiveno = patientarchiveno;
    }

    @Basic
    @Column(name = "PATIENTBIZNO", nullable = true, length = 50)
    public String getPatientbizno() {
        return patientbizno;
    }

    public void setPatientbizno(String patientbizno) {
        this.patientbizno = patientbizno;
    }

    @Basic
    @Column(name = "IDCARDNO", nullable = true, length = 30)
    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    @Basic
    @Column(name = "NUMORGRESV", nullable = true, precision = 0)
    public String getNumorgresv() {
        return numorgresv;
    }

    public void setNumorgresv(String numorgresv) {
        this.numorgresv = numorgresv;
    }

    @Basic
    @Column(name = "PATIENTNAME", nullable = true, length = 50)
    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    @Basic
    @Column(name = "INPUT_CODE", nullable = true, length = 25)
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Basic
    @Column(name = "ID_ORGRESERVATIONGROUP", nullable = true, length = 32)
    public String getIdOrgreservationgroup() {
        return idOrgreservationgroup;
    }

    public void setIdOrgreservationgroup(String idOrgreservationgroup) {
        this.idOrgreservationgroup = idOrgreservationgroup;
    }

    @Basic
    @Column(name = "ID_ORGRESERVATION", nullable = true, length = 32)
    public String getIdOrgreservation() {
        return idOrgreservation;
    }

    public void setIdOrgreservation(String idOrgreservation) {
        this.idOrgreservation = idOrgreservation;
    }

    @Basic
    @Column(name = "ID_ORG", nullable = true, length = 32)
    public String getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(String idOrg) {
        this.idOrg = idOrg;
    }

    @Basic
    @Column(name = "ORG_NAME", nullable = true, length = 500)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Basic
    @Column(name = "ORG_DEPART", nullable = true, length = 300)
    public String getOrgDepart() {
        return orgDepart;
    }

    public void setOrgDepart(String orgDepart) {
        this.orgDepart = orgDepart;
    }

    @Basic
    @Column(name = "HAVE_PRIVATE", nullable = true, length = 32)
    public String getHavePrivate() {
        return havePrivate;
    }

    public void setHavePrivate(String havePrivate) {
        this.havePrivate = havePrivate;
    }

    @Basic
    @Column(name = "ID_PAYWAY", nullable = true, length = 2000)
    public String getIdPayway() {
        return idPayway;
    }

    public void setIdPayway(String idPayway) {
        this.idPayway = idPayway;
    }

    @Basic
    @Column(name = "PAYWAY", nullable = true, length = 4000)
    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    @Basic
    @Column(name = "PERSONPRICELIMIT", nullable = true, precision = 2)
    public Double getPersonpricelimit() {
        return personpricelimit;
    }

    public void setPersonpricelimit(Double personpricelimit) {
        this.personpricelimit = personpricelimit;
    }

    @Basic
    @Column(name = "ID_SEX", nullable = true, length = 32)
    public String getIdSex() {
        return idSex;
    }

    public void setIdSex(String idSex) {
        this.idSex = idSex;
    }

    @Basic
    @Column(name = "BIRTHDATE", nullable = true)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Basic
    @Column(name = "AGE", nullable = true, precision = 1)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "ID_MARRIAGE", nullable = true, length = 32)
    public String getIdMarriage() {
        return idMarriage;
    }

    public void setIdMarriage(String idMarriage) {
        this.idMarriage = idMarriage;
    }

    @Basic
    @Column(name = "MARRIAGE", nullable = true, length = 4)
    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    @Basic
    @Column(name = "ID_NATION", nullable = true, length = 32)
    public String getIdNation() {
        return idNation;
    }

    public void setIdNation(String idNation) {
        this.idNation = idNation;
    }

    @Basic
    @Column(name = "NATION", nullable = true, length = 16)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 120)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "ID_INFORMWAY", nullable = true, length = 32)
    public String getIdInformway() {
        return idInformway;
    }

    public void setIdInformway(String idInformway) {
        this.idInformway = idInformway;
    }

    @Basic
    @Column(name = "ID_OPENDOCTOR", nullable = true, length = 32)
    public String getIdOpendoctor() {
        return idOpendoctor;
    }

    public void setIdOpendoctor(String idOpendoctor) {
        this.idOpendoctor = idOpendoctor;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 80)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 30)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "ID_PATIENTCLASS", nullable = true, length = 32)
    public String getIdPatientclass() {
        return idPatientclass;
    }

    public void setIdPatientclass(String idPatientclass) {
        this.idPatientclass = idPatientclass;
    }

    @Basic
    @Column(name = "ID_RESAREA", nullable = true, length = 32)
    public String getIdResarea() {
        return idResarea;
    }

    public void setIdResarea(String idResarea) {
        this.idResarea = idResarea;
    }

    @Basic
    @Column(name = "RESAREA", nullable = true, length = 40)
    public String getResarea() {
        return resarea;
    }

    public void setResarea(String resarea) {
        this.resarea = resarea;
    }

    @Basic
    @Column(name = "F_ISFORPREPARE", nullable = true, precision = 0)
    public Integer getfIsforprepare() {
        return fIsforprepare;
    }

    public void setfIsforprepare(Integer fIsforprepare) {
        this.fIsforprepare = fIsforprepare;
    }

    @Basic
    @Column(name = "F_ISFORRESERVE", nullable = true, precision = 0)
    public Integer getfIsforreserve() {
        return fIsforreserve;
    }

    public void setfIsforreserve(Integer fIsforreserve) {
        this.fIsforreserve = fIsforreserve;
    }

    @Basic
    @Column(name = "F_REGISTERED", nullable = true, precision = 0)
    public Integer getfRegistered() {
        return fRegistered;
    }

    public void setfRegistered(Integer fRegistered) {
        this.fRegistered = fRegistered;
    }

    @Basic
    @Column(name = "DATEREGISTER", nullable = true)
    public Date getDateregister() {
        return dateregister;
    }

    public void setDateregister(Date dateregister) {
        this.dateregister = dateregister;
    }

    @Basic
    @Column(name = "MONEYAMOUNT", nullable = true, precision = 2)
    public Double getMoneyamount() {
        return moneyamount;
    }

    public void setMoneyamount(Double moneyamount) {
        this.moneyamount = moneyamount;
    }

    @Basic
    @Column(name = "MONEYAMOUNTPAID", nullable = true, precision = 2)
    public Double getMoneyamountpaid() {
        return moneyamountpaid;
    }

    public void setMoneyamountpaid(Double moneyamountpaid) {
        this.moneyamountpaid = moneyamountpaid;
    }

    @Basic
    @Column(name = "GUIDANCENOTE", nullable = true, length = 200)
    public String getGuidancenote() {
        return guidancenote;
    }

    public void setGuidancenote(String guidancenote) {
        this.guidancenote = guidancenote;
    }

    @Basic
    @Column(name = "WORKNO", nullable = true, length = 20)
    public String getWorkno() {
        return workno;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    @Basic
    @Column(name = "ID_DOCTORREG", nullable = true, length = 32)
    public String getIdDoctorreg() {
        return idDoctorreg;
    }

    public void setIdDoctorreg(String idDoctorreg) {
        this.idDoctorreg = idDoctorreg;
    }

    @Basic
    @Column(name = "DOCTORREG", nullable = true, length = 16)
    public String getDoctorreg() {
        return doctorreg;
    }

    public void setDoctorreg(String doctorreg) {
        this.doctorreg = doctorreg;
    }

    @Basic
    @Column(name = "ID_EXAMTYPE", nullable = true, length = 32)
    public String getIdExamtype() {
        return idExamtype;
    }

    public void setIdExamtype(String idExamtype) {
        this.idExamtype = idExamtype;
    }

    @Basic
    @Column(name = "EXAMSUITE_NAME", nullable = true, length = 200)
    public String getExamsuiteName() {
        return examsuiteName;
    }

    public void setExamsuiteName(String examsuiteName) {
        this.examsuiteName = examsuiteName;
    }

    @Basic
    @Column(name = "EXAMSUITE_ALIAS", nullable = true, length = 100)
    public String getExamsuiteAlias() {
        return examsuiteAlias;
    }

    public void setExamsuiteAlias(String examsuiteAlias) {
        this.examsuiteAlias = examsuiteAlias;
    }

    @Basic
    @Column(name = "ID_DOCTORAPPLY", nullable = true, length = 32)
    public String getIdDoctorapply() {
        return idDoctorapply;
    }

    public void setIdDoctorapply(String idDoctorapply) {
        this.idDoctorapply = idDoctorapply;
    }

    @Basic
    @Column(name = "DOCTORAPPLY", nullable = true, length = 16)
    public String getDoctorapply() {
        return doctorapply;
    }

    public void setDoctorapply(String doctorapply) {
        this.doctorapply = doctorapply;
    }

    @Basic
    @Column(name = "F_GUIDANCEPRINTED", nullable = true, precision = 0)
    public Integer getfGuidanceprinted() {
        return fGuidanceprinted;
    }

    public void setfGuidanceprinted(Integer fGuidanceprinted) {
        this.fGuidanceprinted = fGuidanceprinted;
    }

    @Basic
    @Column(name = "F_EXAMSTARTED", nullable = true, precision = 0)
    public Integer getfExamstarted() {
        return fExamstarted;
    }

    public void setfExamstarted(Integer fExamstarted) {
        this.fExamstarted = fExamstarted;
    }

    @Basic
    @Column(name = "F_READYTOFINAL", nullable = true, precision = 0)
    public Integer getfReadytofinal() {
        return fReadytofinal;
    }

    public void setfReadytofinal(Integer fReadytofinal) {
        this.fReadytofinal = fReadytofinal;
    }


    @Basic
    @Column(name = "F_PAUSED", nullable = true, precision = 0)
    public Integer getfPaused() {
        return fPaused;
    }

    public void setfPaused(Integer fPaused) {
        this.fPaused = fPaused;
    }

    @Basic
    @Column(name = "F_FINALLOCKED", nullable = true, precision = 0)
    public Integer getfFinallocked() {
        return fFinallocked;
    }

    public void setfFinallocked(Integer fFinallocked) {
        this.fFinallocked = fFinallocked;
    }

    @Basic
    @Column(name = "F_FINALEXAMED", nullable = true, precision = 0)
    public Integer getfFinalexamed() {
        return fFinalexamed;
    }

    public void setfFinalexamed(Integer fFinalexamed) {
        this.fFinalexamed = fFinalexamed;
    }

    @Basic
    @Column(name = "ID_DOCTORFINAL", nullable = true, length = 32)
    public String getIdDoctorfinal() {
        return idDoctorfinal;
    }

    public void setIdDoctorfinal(String idDoctorfinal) {
        this.idDoctorfinal = idDoctorfinal;
    }

    @Basic
    @Column(name = "DOCTORFINAL_NAME_R", nullable = true, length = 12)
    public String getDoctorfinalNameR() {
        return doctorfinalNameR;
    }

    public void setDoctorfinalNameR(String doctorfinalNameR) {
        this.doctorfinalNameR = doctorfinalNameR;
    }

    @Basic
    @Column(name = "DATEFINALEXAMED", nullable = true)
    public Date getDatefinalexamed() {
        return datefinalexamed;
    }

    public void setDatefinalexamed(Date datefinalexamed) {
        this.datefinalexamed = datefinalexamed;
    }

    @Basic
    @Column(name = "F_CLOSED", nullable = true, precision = 0)
    public Integer getfClosed() {
        return fClosed;
    }

    public void setfClosed(Integer fClosed) {
        this.fClosed = fClosed;
    }

    @Basic
    @Column(name = "DATECLOSED", nullable = true)
    public Date getDateclosed() {
        return dateclosed;
    }

    public void setDateclosed(Date dateclosed) {
        this.dateclosed = dateclosed;
    }

    @Basic
    @Column(name = "NOTE", nullable = true, length = 1000)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "KNOWLEDGE", nullable = true, length = 2000)
    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    @Basic
    @Column(name = "TIMINGSTARTEDAT", nullable = true)
    public Date getTimingstartedat() {
        return timingstartedat;
    }

    public void setTimingstartedat(Date timingstartedat) {
        this.timingstartedat = timingstartedat;
    }

    @Basic
    @Column(name = "HOSPITALCODE", nullable = true, length = 50)
    public String getHospitalcode() {
        return hospitalcode;
    }

    public void setHospitalcode(String hospitalcode) {
        this.hospitalcode = hospitalcode;
    }

    @Basic
    @Column(name = "ID_EXAMPLACE", nullable = true, precision = 0)
    public Integer getIdExamplace() {
        return idExamplace;
    }

    public void setIdExamplace(Integer idExamplace) {
        this.idExamplace = idExamplace;
    }

    @Basic
    @Column(name = "PARSEDASSIGNEDSUITEANDFI", nullable = true, length = 300)
    public String getParsedassignedsuiteandfi() {
        return parsedassignedsuiteandfi;
    }

    public void setParsedassignedsuiteandfi(String parsedassignedsuiteandfi) {
        this.parsedassignedsuiteandfi = parsedassignedsuiteandfi;
    }

    @Basic
    @Column(name = "PARSEDASSIGNEDGROUPANDFI", nullable = true, length = 300)
    public String getParsedassignedgroupandfi() {
        return parsedassignedgroupandfi;
    }

    public void setParsedassignedgroupandfi(String parsedassignedgroupandfi) {
        this.parsedassignedgroupandfi = parsedassignedgroupandfi;
    }

    @Basic
    @Column(name = "PARSEDSUITEANDFI", nullable = true, length = 300)
    public String getParsedsuiteandfi() {
        return parsedsuiteandfi;
    }

    public void setParsedsuiteandfi(String parsedsuiteandfi) {
        this.parsedsuiteandfi = parsedsuiteandfi;
    }

    @Basic
    @Column(name = "PARSEDSUITEANDFILAB", nullable = true, length = 300)
    public String getParsedsuiteandfilab() {
        return parsedsuiteandfilab;
    }

    public void setParsedsuiteandfilab(String parsedsuiteandfilab) {
        this.parsedsuiteandfilab = parsedsuiteandfilab;
    }

    @Basic
    @Column(name = "ID_GUIDENURSE", nullable = true, precision = 0)
    public Integer getIdGuidenurse() {
        return idGuidenurse;
    }

    public void setIdGuidenurse(Integer idGuidenurse) {
        this.idGuidenurse = idGuidenurse;
    }

    @Basic
    @Column(name = "PATIENTNAMEENCODED", nullable = true, length = 100)
    public String getPatientnameencoded() {
        return patientnameencoded;
    }

    public void setPatientnameencoded(String patientnameencoded) {
        this.patientnameencoded = patientnameencoded;
    }

    @Basic
    @Column(name = "PATIENTCODEHIDEN", nullable = true, length = 50)
    public String getPatientcodehiden() {
        return patientcodehiden;
    }

    public void setPatientcodehiden(String patientcodehiden) {
        this.patientcodehiden = patientcodehiden;
    }

    @Basic
    @Column(name = "F_WORDPRINTED", nullable = true, precision = 0)
    public Integer getfWordprinted() {
        return fWordprinted;
    }

    public void setfWordprinted(Integer fWordprinted) {
        this.fWordprinted = fWordprinted;
    }

    @Basic
    @Column(name = "GUIDANCENOTE2", nullable = true, length = 200)
    public String getGuidancenote2() {
        return guidancenote2;
    }

    public void setGuidancenote2(String guidancenote2) {
        this.guidancenote2 = guidancenote2;
    }

    @Basic
    @Column(name = "F_USECODEHIDEN", nullable = true, precision = 0)
    public Integer getfUsecodehiden() {
        return fUsecodehiden;
    }

    public void setfUsecodehiden(Integer fUsecodehiden) {
        this.fUsecodehiden = fUsecodehiden;
    }

    @Basic
    @Column(name = "ID_PATIENTCLASS3", nullable = true, precision = 0)
    public Integer getIdPatientclass3() {
        return idPatientclass3;
    }

    public void setIdPatientclass3(Integer idPatientclass3) {
        this.idPatientclass3 = idPatientclass3;
    }

    @Basic
    @Column(name = "DATEREGISTERNOTIME", nullable = true)
    public Date getDateregisternotime() {
        return dateregisternotime;
    }

    public void setDateregisternotime(Date dateregisternotime) {
        this.dateregisternotime = dateregisternotime;
    }

    @Basic
    @Column(name = "COUNTERREPORTPRINTED", nullable = true, precision = 0)
    public Integer getCounterreportprinted() {
        return counterreportprinted;
    }

    public void setCounterreportprinted(Integer counterreportprinted) {
        this.counterreportprinted = counterreportprinted;
    }

    @Basic
    @Column(name = "F_ISRECHECK", nullable = true, precision = 0)
    public Integer getfIsrecheck() {
        return fIsrecheck;
    }

    public void setfIsrecheck(Integer fIsrecheck) {
        this.fIsrecheck = fIsrecheck;
    }

    @Basic
    @Column(name = "F_SETTLENONE", nullable = true, precision = 0)
    public Integer getfSettlenone() {
        return fSettlenone;
    }

    public void setfSettlenone(Integer fSettlenone) {
        this.fSettlenone = fSettlenone;
    }

    @Basic
    @Column(name = "DATEGUIDANCERETURNED", nullable = true)
    public Date getDateguidancereturned() {
        return dateguidancereturned;
    }

    public void setDateguidancereturned(Date dateguidancereturned) {
        this.dateguidancereturned = dateguidancereturned;
    }

    @Basic
    @Column(name = "F_OUTPATIENT", nullable = true, precision = 0)
    public Integer getfOutpatient() {
        return fOutpatient;
    }

    public void setfOutpatient(Integer fOutpatient) {
        this.fOutpatient = fOutpatient;
    }

    @Basic
    @Column(name = "PATIENTNAMERECEIPT", nullable = true, length = 50)
    public String getPatientnamereceipt() {
        return patientnamereceipt;
    }

    public void setPatientnamereceipt(String patientnamereceipt) {
        this.patientnamereceipt = patientnamereceipt;
    }

    @Basic
    @Column(name = "PATIENTNAMEPINYIN", nullable = true, length = 50)
    public String getPatientnamepinyin() {
        return patientnamepinyin;
    }

    public void setPatientnamepinyin(String patientnamepinyin) {
        this.patientnamepinyin = patientnamepinyin;
    }

    @Basic
    @Column(name = "STATUSOFHM", nullable = true, length = 10)
    public String getStatusofhm() {
        return statusofhm;
    }

    public void setStatusofhm(String statusofhm) {
        this.statusofhm = statusofhm;
    }

    @Basic
    @Column(name = "INSTANCETAG", nullable = true, length = 30)
    public String getInstancetag() {
        return instancetag;
    }

    public void setInstancetag(String instancetag) {
        this.instancetag = instancetag;
    }

    @Basic
    @Column(name = "INPATIENTNO", nullable = true, length = 50)
    public String getInpatientno() {
        return inpatientno;
    }

    public void setInpatientno(String inpatientno) {
        this.inpatientno = inpatientno;
    }

    @Basic
    @Column(name = "INSURANCENO", nullable = true, length = 50)
    public String getInsuranceno() {
        return insuranceno;
    }

    public void setInsuranceno(String insuranceno) {
        this.insuranceno = insuranceno;
    }

    @Basic
    @Column(name = "COUNTREPORTXML", nullable = true, precision = 0)
    public Integer getCountreportxml() {
        return countreportxml;
    }

    public void setCountreportxml(Integer countreportxml) {
        this.countreportxml = countreportxml;
    }

    @Basic
    @Column(name = "COUNTREPORTCOMPARE", nullable = true, precision = 0)
    public Integer getCountreportcompare() {
        return countreportcompare;
    }

    public void setCountreportcompare(Integer countreportcompare) {
        this.countreportcompare = countreportcompare;
    }

    @Basic
    @Column(name = "COUNTREPORTOCCUPATION", nullable = true, precision = 0)
    public Integer getCountreportoccupation() {
        return countreportoccupation;
    }

    public void setCountreportoccupation(Integer countreportoccupation) {
        this.countreportoccupation = countreportoccupation;
    }

    @Basic
    @Column(name = "COUNTREPORTOCCUPATIONPDF", nullable = true, precision = 0)
    public Integer getCountreportoccupationpdf() {
        return countreportoccupationpdf;
    }

    public void setCountreportoccupationpdf(Integer countreportoccupationpdf) {
        this.countreportoccupationpdf = countreportoccupationpdf;
    }

    @Basic
    @Column(name = "COUNTREPORTOCCUPATIONXML", nullable = true, precision = 0)
    public Integer getCountreportoccupationxml() {
        return countreportoccupationxml;
    }

    public void setCountreportoccupationxml(Integer countreportoccupationxml) {
        this.countreportoccupationxml = countreportoccupationxml;
    }

    @Basic
    @Column(name = "ID_TJTC", nullable = true, length = 32)
    public String getIdTjtc() {
        return idTjtc;
    }

    public void setIdTjtc(String idTjtc) {
        this.idTjtc = idTjtc;
    }

    @Basic
    @Column(name = "JZDW", nullable = true, length = 100)
    public String getJzdw() {
        return jzdw;
    }

    public void setJzdw(String jzdw) {
        this.jzdw = jzdw;
    }

    @Basic
    @Column(name = "JZDWR", nullable = true, length = 20)
    public String getJzdwr() {
        return jzdwr;
    }

    public void setJzdwr(String jzdwr) {
        this.jzdwr = jzdwr;
    }

    @Basic
    @Column(name = "SPR", nullable = true, length = 20)
    public String getSpr() {
        return spr;
    }

    public void setSpr(String spr) {
        this.spr = spr;
    }

    @Basic
    @Column(name = "TJR", nullable = true, length = 50)
    public String getTjr() {
        return tjr;
    }

    public void setTjr(String tjr) {
        this.tjr = tjr;
    }

    @Basic
    @Column(name = "LQFS", nullable = true, length = 10)
    public String getLqfs() {
        return lqfs;
    }

    public void setLqfs(String lqfs) {
        this.lqfs = lqfs;
    }

    @Basic
    @Column(name = "YZBM", nullable = true, precision = 0)
    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    @Basic
    @Column(name = "YJADDRESS", nullable = true, length = 200)
    public String getYjaddress() {
        return yjaddress;
    }

    public void setYjaddress(String yjaddress) {
        this.yjaddress = yjaddress;
    }

    @Basic
    @Column(name = "QTXZ", nullable = true, length = 2000)
    public String getQtxz() {
        return qtxz;
    }

    public void setQtxz(String qtxz) {
        this.qtxz = qtxz;
    }

    @Basic
    @Column(name = "IS_HMDB", nullable = true, length = 2000)
    public String getIsHmdb() {
        return isHmdb;
    }

    public void setIsHmdb(String isHmdb) {
        this.isHmdb = isHmdb;
    }


    @Basic
    @Column(name = "ISJJ", nullable = true, precision = 0)
    public Integer getIsjj() {
        return isjj;
    }

    public void setIsjj(Integer isjj) {
        this.isjj = isjj;
    }

    @Basic
    @Column(name = "ZGL", nullable = true, precision = 0)
    public Long getZgl() {
        return zgl;
    }

    public void setZgl(Long zgl) {
        this.zgl = zgl;
    }

    @Basic
    @Column(name = "JHGL", nullable = true, precision = 0)
    public Long getJhgl() {
        return jhgl;
    }

    public void setJhgl(Long jhgl) {
        this.jhgl = jhgl;
    }

    @Basic
    @Column(name = "JHYS", nullable = true, length = 2000)
    public String getJhys() {
        return jhys;
    }

    public void setJhys(String jhys) {
        this.jhys = jhys;
    }

    @Basic
    @Column(name = "JKTJZT", nullable = true, precision = 0)
    public Long getJktjzt() {
        return jktjzt;
    }

    public void setJktjzt(Long jktjzt) {
        this.jktjzt = jktjzt;
    }

    @Basic
    @Column(name = "ZYTJZT", nullable = true, precision = 0)
    public Long getZytjzt() {
        return zytjzt;
    }

    public void setZytjzt(Long zytjzt) {
        this.zytjzt = zytjzt;
    }

    @Basic
    @Column(name = "TMYD", nullable = true, precision = 0)
    public Long getTmyd() {
        return tmyd;
    }

    public void setTmyd(Long tmyd) {
        this.tmyd = tmyd;
    }

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MEDICALDATE", nullable = true)
    public Date getMedicaldate() {
        return medicaldate;
    }

    public void setMedicaldate(Date medicaldate) {
        this.medicaldate = medicaldate;
    }

    @Basic
    @Column(name = "TRADES", nullable = true, length = 50)
    public String getTrades() {
        return trades;
    }

    public void setTrades(String trades) {
        this.trades = trades;
    }

    @Basic
    @Column(name = "CJJGSFYHF", nullable = true, precision = 0)
    public Integer getCjjgsfyhf() {
        return cjjgsfyhf;
    }

    public void setCjjgsfyhf(Integer cjjgsfyhf) {
        this.cjjgsfyhf = cjjgsfyhf;
    }

    @Basic
    @Column(name = "BHGYBSFYHF", nullable = true, precision = 0)
    public Integer getBhgybsfyhf() {
        return bhgybsfyhf;
    }

    public void setBhgybsfyhf(Integer bhgybsfyhf) {
        this.bhgybsfyhf = bhgybsfyhf;
    }

    @Basic
    @Column(name = "YXJGSFYHF", nullable = true, precision = 0)
    public Integer getYxjgsfyhf() {
        return yxjgsfyhf;
    }

    public void setYxjgsfyhf(Integer yxjgsfyhf) {
        this.yxjgsfyhf = yxjgsfyhf;
    }

    @Basic
    @Column(name = "MEDICALTYPE", nullable = true, length = 20)
    public String getMedicaltype() {
        return medicaltype;
    }

    public void setMedicaltype(String medicaltype) {
        this.medicaltype = medicaltype;
    }

    @Basic
    @Column(name = "PREPAYMENT", nullable = true, precision = 2)
    public Long getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(Long prepayment) {
        this.prepayment = prepayment;
    }

    @Basic
    @Column(name = "TCPRICE", nullable = true, precision = 2)
    public Long getTcprice() {
        return tcprice;
    }

    public void setTcprice(Long tcprice) {
        this.tcprice = tcprice;
    }

    @Basic
    @Column(name = "CREATEDATE", nullable = true)
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE", nullable = true)
    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "CULTURAL", nullable = true, precision = 0)
    public Integer getCultural() {
        return cultural;
    }

    public void setCultural(Integer cultural) {
        this.cultural = cultural;
    }

    @Basic
    @Column(name = "WORK_DATE", nullable = true)
    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    @Basic
    @Column(name = "HARM_DATE", nullable = true)
    public Date getHarmDate() {
        return harmDate;
    }

    public void setHarmDate(Date harmDate) {
        this.harmDate = harmDate;
    }

    @Basic
    @Column(name = "DISEASE_PRINT_NUM", nullable = true, precision = 0)
    public Integer getDiseasePrintNum() {
        return diseasePrintNum;
    }

    public void setDiseasePrintNum(Integer diseasePrintNum) {
        this.diseasePrintNum = diseasePrintNum;
    }

    @Basic
    @Column(name = "HEALTH_PRINT_NUM", nullable = true, precision = 0)
    public Integer getHealthPrintNum() {
        return healthPrintNum;
    }

    public void setHealthPrintNum(Integer healthPrintNum) {
        this.healthPrintNum = healthPrintNum;
    }

    @Basic
    @Column(name = "READYTOFINAL_DATE", nullable = true)
    public Date getReadytofinalDate() {
        return readytofinalDate;
    }

    public void setReadytofinalDate(Date readytofinalDate) {
        this.readytofinalDate = readytofinalDate;
    }

    @Basic
    @Column(name = "GUIDE_SIGNLE_COUNT", nullable = true, precision = 0)
    public Integer getGuideSignleCount() {
        return guideSignleCount;
    }

    public void setGuideSignleCount(Integer guideSignleCount) {
        this.guideSignleCount = guideSignleCount;
    }

    @Basic
    @Column(name = "SHORT_CODE", nullable = true, precision = 0)
    public Integer getShortCode() {
        return shortCode;
    }

    public void setShortCode(Integer shortCode) {
        this.shortCode = shortCode;
    }


    @Basic
    @Column(name = "REVIEW_PDF", nullable = true, length = 200)
    public String getReviewPdf() {
        return reviewPdf;
    }

    public void setReviewPdf(String reviewPdf) {
        this.reviewPdf = reviewPdf;
    }

    @Basic
    @Column(name = "CONTRAINDICATED_PDF", nullable = true, length = 200)
    public String getContraindicatedPdf() {
        return contraindicatedPdf;
    }

    public void setContraindicatedPdf(String contraindicatedPdf) {
        this.contraindicatedPdf = contraindicatedPdf;
    }

    @Basic
    @Column(name = "DISEASE_PDF", nullable = true, length = 200)
    public String getDiseasePdf() {
        return diseasePdf;
    }

    public void setDiseasePdf(String diseasePdf) {
        this.diseasePdf = diseasePdf;
    }

    @Basic
    @Column(name = "TS_LIMIT", nullable = true, precision = 2)
    public Double getTsLimit() {
        return tsLimit;
    }

    public void setTsLimit(Double tsLimit) {
        this.tsLimit = tsLimit;
    }

    @Basic
    @Column(name = "CHECKOUT_DATE", nullable = true)
    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    @Basic
    @Column(name = "CHECKOUT_STATUS", nullable = true, precision = 0)
    public Integer getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(Integer checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }


    @Basic
    @Column(name = "WORKTYPE_ID", nullable = true, length = 32)
    public String getWorktypeId() {
        return worktypeId;
    }

    public void setWorktypeId(String worktypeId) {
        this.worktypeId = worktypeId;
    }
}