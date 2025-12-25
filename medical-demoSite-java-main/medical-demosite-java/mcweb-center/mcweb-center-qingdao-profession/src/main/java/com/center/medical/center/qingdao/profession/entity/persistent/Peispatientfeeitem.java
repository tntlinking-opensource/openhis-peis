package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_PEISPATIENTFEEITEM")
public class Peispatientfeeitem {
    private String idPatientfeeitem;
    private String idPatient;
    private String idExamfeeitem;
    private String examfeeitemName;
    private Double price;
    private Double factprice;
    private Double settleprice;
    private Integer fAddeditem;
    private String idPayway;
    private Integer fRegistered;
    private String idDoctorreg;
    private String doctorregR;
    private Date registerTime;
    private String idPatientregistersheet;
    private Integer fRegreturned;
    private Integer fFeecharged;
    private String idFeecharger;
    private String feechargerNameR;
    private Date feechargeTime;
    private String idFeediscounter;
    private Double batchnumber;
    private Double tubeposition;
    private Double samplenumber;
    private Integer fLabsampled;
    private String idLabsampler;
    private String labsamplerNameR;
    private Date labsampleTime;
    private Integer fLabsendtolis;
    private Date labsendtolisTime;
    private Integer fLabrcvdfromlis;
    private Date labrcvdfromlisTime;
    private Integer fGiveup;
    private Integer fExaminated;
    private String idPatientexamdepart;
    private String idExamdoctor;
    private String examdoctorNameR;
    private Date examinateTime;
    private Integer fMarkFeereturn;
    private Integer fWorkInnerModify;
    private Integer severedegree;
    private String positivesummary;
    private String interfacemarks;
    private String urlresult;
    private Integer fDelayed;
    private Date dtDelayedtill;
    private String notewhydelayed;
    private String idExamplace;
    private Integer fTransferedhl7;
    private Double qty;
    private String feeitemdesc;
    private String feeitemsummary;
    private String idTypist;
    private String idExamapprovedby;
    private String examapprovedbyNameR;
    private String samplenumberfromlis;
    private String samplemsgfromlis;
    private String receiptsheetnofromhis;
    private String feeitemrequestno;
    private String samplestatus;
    private String backupsingleitemcopiesprinted;
    private Integer fFeechargedinttrans;
    private String giveupnotelet;
    private Date createDate;
    private Date modifyDate;
    private Integer sfjx;
    private String jxys;
    private String idKs;
    private Integer count;
    private String id;
    private Date createdate;
    private Date modifydate;
    private Integer changeItem;
    private Integer isMintc;
    private Integer isbx;
    private Integer bxcount;
    private Double endtuiprice;
    private Double actualprice;
    private Double shortCode;
    private String qjr;
    private String bjr;
    private String cjr;
    private Date qjsj;
    private Date bjsj;
    private Date cjsj;
    private Integer sfjj;
    private String jjr;
    private Date jjsj;
    private String jjrqm;
    private Integer itemGroup;
    private String adiconCode;
    private String chargeId;
    private String tradeNo;

    @Basic
    @Column(name = "ID_PATIENTFEEITEM", nullable = true, length = 32)
    public String getIdPatientfeeitem() {
        return idPatientfeeitem;
    }

    public void setIdPatientfeeitem(String idPatientfeeitem) {
        this.idPatientfeeitem = idPatientfeeitem;
    }

    @Basic
    @Column(name = "ID_PATIENT", nullable = true, length = 50)
    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    @Basic
    @Column(name = "ID_EXAMFEEITEM", nullable = true, length = 32)
    public String getIdExamfeeitem() {
        return idExamfeeitem;
    }

    public void setIdExamfeeitem(String idExamfeeitem) {
        this.idExamfeeitem = idExamfeeitem;
    }

    @Basic
    @Column(name = "EXAMFEEITEM_NAME", nullable = true, length = 100)
    public String getExamfeeitemName() {
        return examfeeitemName;
    }

    public void setExamfeeitemName(String examfeeitemName) {
        this.examfeeitemName = examfeeitemName;
    }

    @Basic
    @Column(name = "PRICE", nullable = true, precision = 2)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "FACTPRICE", nullable = true, precision = 2)
    public Double getFactprice() {
        return factprice;
    }

    public void setFactprice(Double factprice) {
        this.factprice = factprice;
    }

    @Basic
    @Column(name = "SETTLEPRICE", nullable = true, precision = 2)
    public Double getSettleprice() {
        return settleprice;
    }

    public void setSettleprice(Double settleprice) {
        this.settleprice = settleprice;
    }

    @Basic
    @Column(name = "F_ADDEDITEM", nullable = true, precision = 0)
    public Integer getfAddeditem() {
        return fAddeditem;
    }

    public void setfAddeditem(Integer fAddeditem) {
        this.fAddeditem = fAddeditem;
    }

    @Basic
    @Column(name = "ID_PAYWAY", nullable = true, length = 32)
    public String getIdPayway() {
        return idPayway;
    }

    public void setIdPayway(String idPayway) {
        this.idPayway = idPayway;
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
    @Column(name = "ID_DOCTORREG", nullable = true, length = 32)
    public String getIdDoctorreg() {
        return idDoctorreg;
    }

    public void setIdDoctorreg(String idDoctorreg) {
        this.idDoctorreg = idDoctorreg;
    }

    @Basic
    @Column(name = "DOCTORREG_R", nullable = true, length = 16)
    public String getDoctorregR() {
        return doctorregR;
    }

    public void setDoctorregR(String doctorregR) {
        this.doctorregR = doctorregR;
    }

    @Basic
    @Column(name = "REGISTERTIME", nullable = true)
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "ID_PATIENTREGISTERSHEET", nullable = true, length = 32)
    public String getIdPatientregistersheet() {
        return idPatientregistersheet;
    }

    public void setIdPatientregistersheet(String idPatientregistersheet) {
        this.idPatientregistersheet = idPatientregistersheet;
    }

    @Basic
    @Column(name = "F_REGRETURNED", nullable = true, precision = 0)
    public Integer getfRegreturned() {
        return fRegreturned;
    }

    public void setfRegreturned(Integer fRegreturned) {
        this.fRegreturned = fRegreturned;
    }

    @Basic
    @Column(name = "F_FEECHARGED", nullable = true, precision = 0)
    public Integer getfFeecharged() {
        return fFeecharged;
    }

    public void setfFeecharged(Integer fFeecharged) {
        this.fFeecharged = fFeecharged;
    }

    @Basic
    @Column(name = "ID_FEECHARGER", nullable = true, length = 32)
    public String getIdFeecharger() {
        return idFeecharger;
    }

    public void setIdFeecharger(String idFeecharger) {
        this.idFeecharger = idFeecharger;
    }

    @Basic
    @Column(name = "FEECHARGER_NAME_R", nullable = true, length = 16)
    public String getFeechargerNameR() {
        return feechargerNameR;
    }

    public void setFeechargerNameR(String feechargerNameR) {
        this.feechargerNameR = feechargerNameR;
    }

    @Basic
    @Column(name = "FEECHARGETIME", nullable = true)
    public Date getFeechargeTime() {
        return feechargeTime;
    }

    public void setFeechargeTime(Date feechargeDate) {
        this.feechargeTime = feechargeDate;
    }

    @Basic
    @Column(name = "ID_FEEDISCOUNTER", nullable = true, length = 32)
    public String getIdFeediscounter() {
        return idFeediscounter;
    }

    public void setIdFeediscounter(String idFeediscounter) {
        this.idFeediscounter = idFeediscounter;
    }

    @Basic
    @Column(name = "BATCHNUMBER", nullable = true, precision = 0)
    public Double getBatchnumber() {
        return batchnumber;
    }

    public void setBatchnumber(Double batchnumber) {
        this.batchnumber = batchnumber;
    }

    @Basic
    @Column(name = "TUBEPOSITION", nullable = true, precision = 0)
    public Double getTubeposition() {
        return tubeposition;
    }

    public void setTubeposition(Double tubeposition) {
        this.tubeposition = tubeposition;
    }

    @Basic
    @Column(name = "SAMPLENUMBER", nullable = true, precision = 0)
    public Double getSamplenumber() {
        return samplenumber;
    }

    public void setSamplenumber(Double samplenumber) {
        this.samplenumber = samplenumber;
    }

    @Basic
    @Column(name = "F_LABSAMPLED", nullable = true, precision = 0)
    public Integer getfLabsampled() {
        return fLabsampled;
    }

    public void setfLabsampled(Integer fLabsampled) {
        this.fLabsampled = fLabsampled;
    }

    @Basic
    @Column(name = "ID_LABSAMPLER", nullable = true, length = 32)
    public String getIdLabsampler() {
        return idLabsampler;
    }

    public void setIdLabsampler(String idLabsampler) {
        this.idLabsampler = idLabsampler;
    }

    @Basic
    @Column(name = "LABSAMPLER_NAME_R", nullable = true, length = 16)
    public String getLabsamplerNameR() {
        return labsamplerNameR;
    }

    public void setLabsamplerNameR(String labsamplerNameR) {
        this.labsamplerNameR = labsamplerNameR;
    }

    @Basic
    @Column(name = "LABSAMPLEtime", nullable = true)
    public Date getLabsampleTime() {
        return labsampleTime;
    }

    public void setLabsampleTime(Date labsampleDate) {
        this.labsampleTime = labsampleDate;
    }

    @Basic
    @Column(name = "F_LABSENDTOLIS", nullable = true, precision = 0)
    public Integer getfLabsendtolis() {
        return fLabsendtolis;
    }

    public void setfLabsendtolis(Integer fLabsendtolis) {
        this.fLabsendtolis = fLabsendtolis;
    }

    @Basic
    @Column(name = "LABSENDTOLISTime", nullable = true)
    public Date getLabsendtolisTime() {
        return labsendtolisTime;
    }

    public void setLabsendtolisTime(Date labsendtolisDate) {
        this.labsendtolisTime = labsendtolisDate;
    }

    @Basic
    @Column(name = "F_LABRCVDFROMLIS", nullable = true, precision = 0)
    public Integer getfLabrcvdfromlis() {
        return fLabrcvdfromlis;
    }

    public void setfLabrcvdfromlis(Integer fLabrcvdfromlis) {
        this.fLabrcvdfromlis = fLabrcvdfromlis;
    }

    @Basic
    @Column(name = "LABRCVDFROMLISTime", nullable = true)
    public Date getLabrcvdfromlisTime() {
        return labrcvdfromlisTime;
    }

    public void setLabrcvdfromlisTime(Date labrcvdfromlisDate) {
        this.labrcvdfromlisTime = labrcvdfromlisDate;
    }

    @Basic
    @Column(name = "F_GIVEUP", nullable = true, precision = 0)
    public Integer getfGiveup() {
        return fGiveup;
    }

    public void setfGiveup(Integer fGiveup) {
        this.fGiveup = fGiveup;
    }

    @Basic
    @Column(name = "F_EXAMINATED", nullable = true, precision = 0)
    public Integer getfExaminated() {
        return fExaminated;
    }

    public void setfExaminated(Integer fExaminated) {
        this.fExaminated = fExaminated;
    }

    @Basic
    @Column(name = "ID_PATIENTEXAMDEPART", nullable = true, length = 32)
    public String getIdPatientexamdepart() {
        return idPatientexamdepart;
    }

    public void setIdPatientexamdepart(String idPatientexamdepart) {
        this.idPatientexamdepart = idPatientexamdepart;
    }

    @Basic
    @Column(name = "ID_EXAMDOCTOR", nullable = true, length = 32)
    public String getIdExamdoctor() {
        return idExamdoctor;
    }

    public void setIdExamdoctor(String idExamdoctor) {
        this.idExamdoctor = idExamdoctor;
    }

    @Basic
    @Column(name = "EXAMDOCTOR_NAME_R", nullable = true, length = 16)
    public String getExamdoctorNameR() {
        return examdoctorNameR;
    }

    public void setExamdoctorNameR(String examdoctorNameR) {
        this.examdoctorNameR = examdoctorNameR;
    }

    @Basic
    @Column(name = "EXAMINATETIME", nullable = true)
    public Date getExaminateTime() {
        return examinateTime;
    }

    public void setExaminateTime(Date examinateDate) {
        this.examinateTime = examinateDate;
    }

    @Basic
    @Column(name = "F_MARK_FEERETURN", nullable = true, precision = 0)
    public Integer getfMarkFeereturn() {
        return fMarkFeereturn;
    }

    public void setfMarkFeereturn(Integer fMarkFeereturn) {
        this.fMarkFeereturn = fMarkFeereturn;
    }

    @Basic
    @Column(name = "F_WORK_INNER_MODIFY", nullable = true, precision = 0)
    public Integer getfWorkInnerModify() {
        return fWorkInnerModify;
    }

    public void setfWorkInnerModify(Integer fWorkInnerModify) {
        this.fWorkInnerModify = fWorkInnerModify;
    }

    @Basic
    @Column(name = "SEVEREDEGREE", nullable = true, precision = 0)
    public Integer getSeveredegree() {
        return severedegree;
    }

    public void setSeveredegree(Integer severedegree) {
        this.severedegree = severedegree;
    }

    @Basic
    @Column(name = "POSITIVESUMMARY", nullable = true, length = 4000)
    public String getPositivesummary() {
        return positivesummary;
    }

    public void setPositivesummary(String positivesummary) {
        this.positivesummary = positivesummary;
    }

    @Basic
    @Column(name = "INTERFACEMARKS", nullable = true, length = 50)
    public String getInterfacemarks() {
        return interfacemarks;
    }

    public void setInterfacemarks(String interfacemarks) {
        this.interfacemarks = interfacemarks;
    }

    @Basic
    @Column(name = "URLRESULT", nullable = true, length = 500)
    public String getUrlresult() {
        return urlresult;
    }

    public void setUrlresult(String urlresult) {
        this.urlresult = urlresult;
    }

    @Basic
    @Column(name = "F_DELAYED", nullable = true, precision = 0)
    public Integer getfDelayed() {
        return fDelayed;
    }

    public void setfDelayed(Integer fDelayed) {
        this.fDelayed = fDelayed;
    }

    @Basic
    @Column(name = "DT_DELAYEDTILL", nullable = true)
    public Date getDtDelayedtill() {
        return dtDelayedtill;
    }

    public void setDtDelayedtill(Date dtDelayedtill) {
        this.dtDelayedtill = dtDelayedtill;
    }

    @Basic
    @Column(name = "NOTEWHYDELAYED", nullable = true, length = 200)
    public String getNotewhydelayed() {
        return notewhydelayed;
    }

    public void setNotewhydelayed(String notewhydelayed) {
        this.notewhydelayed = notewhydelayed;
    }

    @Basic
    @Column(name = "ID_EXAMPLACE", nullable = true, length = 32)
    public String getIdExamplace() {
        return idExamplace;
    }

    public void setIdExamplace(String idExamplace) {
        this.idExamplace = idExamplace;
    }

    @Basic
    @Column(name = "F_TRANSFEREDHL7", nullable = true, precision = 0)
    public Integer getfTransferedhl7() {
        return fTransferedhl7;
    }

    public void setfTransferedhl7(Integer fTransferedhl7) {
        this.fTransferedhl7 = fTransferedhl7;
    }

    @Basic
    @Column(name = "QTY", nullable = true, precision = 2)
    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "FEEITEMDESC", nullable = true, length = 200)
    public String getFeeitemdesc() {
        return feeitemdesc;
    }

    public void setFeeitemdesc(String feeitemdesc) {
        this.feeitemdesc = feeitemdesc;
    }

    @Basic
    @Column(name = "FEEITEMSUMMARY", nullable = true, length = 200)
    public String getFeeitemsummary() {
        return feeitemsummary;
    }

    public void setFeeitemsummary(String feeitemsummary) {
        this.feeitemsummary = feeitemsummary;
    }

    @Basic
    @Column(name = "ID_TYPIST", nullable = true, length = 32)
    public String getIdTypist() {
        return idTypist;
    }

    public void setIdTypist(String idTypist) {
        this.idTypist = idTypist;
    }

    @Basic
    @Column(name = "ID_EXAMAPPROVEDBY", nullable = true, length = 32)
    public String getIdExamapprovedby() {
        return idExamapprovedby;
    }

    public void setIdExamapprovedby(String idExamapprovedby) {
        this.idExamapprovedby = idExamapprovedby;
    }

    @Basic
    @Column(name = "EXAMAPPROVEDBY_NAME_R", nullable = true, length = 16)
    public String getExamapprovedbyNameR() {
        return examapprovedbyNameR;
    }

    public void setExamapprovedbyNameR(String examapprovedbyNameR) {
        this.examapprovedbyNameR = examapprovedbyNameR;
    }

    @Basic
    @Column(name = "SAMPLENUMBERFROMLIS", nullable = true, length = 500)
    public String getSamplenumberfromlis() {
        return samplenumberfromlis;
    }

    public void setSamplenumberfromlis(String samplenumberfromlis) {
        this.samplenumberfromlis = samplenumberfromlis;
    }

    @Basic
    @Column(name = "SAMPLEMSGFROMLIS", nullable = true, length = 500)
    public String getSamplemsgfromlis() {
        return samplemsgfromlis;
    }

    public void setSamplemsgfromlis(String samplemsgfromlis) {
        this.samplemsgfromlis = samplemsgfromlis;
    }

    @Basic
    @Column(name = "RECEIPTSHEETNOFROMHIS", nullable = true, length = 60)
    public String getReceiptsheetnofromhis() {
        return receiptsheetnofromhis;
    }

    public void setReceiptsheetnofromhis(String receiptsheetnofromhis) {
        this.receiptsheetnofromhis = receiptsheetnofromhis;
    }

    @Basic
    @Column(name = "FEEITEMREQUESTNO", nullable = true, length = 50)
    public String getFeeitemrequestno() {
        return feeitemrequestno;
    }

    public void setFeeitemrequestno(String feeitemrequestno) {
        this.feeitemrequestno = feeitemrequestno;
    }

    @Basic
    @Column(name = "SAMPLESTATUS", nullable = true, length = 10)
    public String getSamplestatus() {
        return samplestatus;
    }

    public void setSamplestatus(String samplestatus) {
        this.samplestatus = samplestatus;
    }

    @Basic
    @Column(name = "BACKUPSINGLEITEMCOPIESPRINTED", nullable = true, length = 32)
    public String getBackupsingleitemcopiesprinted() {
        return backupsingleitemcopiesprinted;
    }

    public void setBackupsingleitemcopiesprinted(String backupsingleitemcopiesprinted) {
        this.backupsingleitemcopiesprinted = backupsingleitemcopiesprinted;
    }

    @Basic
    @Column(name = "F_FEECHARGEDINTTRANS", nullable = true, precision = 0)
    public Integer getfFeechargedinttrans() {
        return fFeechargedinttrans;
    }

    public void setfFeechargedinttrans(Integer fFeechargedinttrans) {
        this.fFeechargedinttrans = fFeechargedinttrans;
    }

    @Basic
    @Column(name = "GIVEUPNOTELET", nullable = true, length = 50)
    public String getGiveupnotelet() {
        return giveupnotelet;
    }

    public void setGiveupnotelet(String giveupnotelet) {
        this.giveupnotelet = giveupnotelet;
    }

    @Basic
    @Column(name = "CREATE_DATE", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Basic
    @Column(name = "SFJX", nullable = true, precision = 0)
    public Integer getSfjx() {
        return sfjx;
    }

    public void setSfjx(Integer sfjx) {
        this.sfjx = sfjx;
    }

    @Basic
    @Column(name = "JXYS", nullable = true, length = 32)
    public String getJxys() {
        return jxys;
    }

    public void setJxys(String jxys) {
        this.jxys = jxys;
    }

    @Basic
    @Column(name = "ID_KS", nullable = true, length = 32)
    public String getIdKs() {
        return idKs;
    }

    public void setIdKs(String idKs) {
        this.idKs = idKs;
    }

    @Basic
    @Column(name = "COUNT", nullable = true, precision = 0)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
    @Column(name = "CHANGE_ITEM", nullable = true, precision = 0)
    public Integer getChangeItem() {
        return changeItem;
    }

    public void setChangeItem(Integer changeItem) {
        this.changeItem = changeItem;
    }

    @Basic
    @Column(name = "IS_MINTC", nullable = true, precision = 0)
    public Integer getMintc() {
        return isMintc;
    }

    public void setMintc(Integer mintc) {
        isMintc = mintc;
    }

    @Basic
    @Column(name = "ISBX", nullable = true, precision = 0)
    public Integer getIsbx() {
        return isbx;
    }

    public void setIsbx(Integer isbx) {
        this.isbx = isbx;
    }

    @Basic
    @Column(name = "BXCOUNT", nullable = true, precision = 0)
    public Integer getBxcount() {
        return bxcount;
    }

    public void setBxcount(Integer bxcount) {
        this.bxcount = bxcount;
    }

    @Basic
    @Column(name = "ENDTUIPRICE", nullable = true, precision = 2)
    public Double getEndtuiprice() {
        return endtuiprice;
    }

    public void setEndtuiprice(Double endtuiprice) {
        this.endtuiprice = endtuiprice;
    }

    @Basic
    @Column(name = "ACTUALPRICE", nullable = true, precision = 2)
    public Double getActualprice() {
        return actualprice;
    }

    public void setActualprice(Double actualprice) {
        this.actualprice = actualprice;
    }

    @Basic
    @Column(name = "SHORT_CODE", nullable = true, precision = 0)
    public Double getShortCode() {
        return shortCode;
    }

    public void setShortCode(Double shortCode) {
        this.shortCode = shortCode;
    }

    @Basic
    @Column(name = "QJR", nullable = true, length = 255)
    public String getQjr() {
        return qjr;
    }

    public void setQjr(String qjr) {
        this.qjr = qjr;
    }

    @Basic
    @Column(name = "BJR", nullable = true, length = 255)
    public String getBjr() {
        return bjr;
    }

    public void setBjr(String bjr) {
        this.bjr = bjr;
    }

    @Basic
    @Column(name = "CJR", nullable = true, length = 255)
    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    @Basic
    @Column(name = "QJSJ", nullable = true)
    public Date getQjsj() {
        return qjsj;
    }

    public void setQjsj(Date qjsj) {
        this.qjsj = qjsj;
    }

    @Basic
    @Column(name = "BJSJ", nullable = true)
    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }

    @Basic
    @Column(name = "CJSJ", nullable = true)
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @Basic
    @Column(name = "SFJJ", nullable = true, precision = 0)
    public Integer getSfjj() {
        return sfjj;
    }

    public void setSfjj(Integer sfjj) {
        this.sfjj = sfjj;
    }

    @Basic
    @Column(name = "JJR", nullable = true, length = 32)
    public String getJjr() {
        return jjr;
    }

    public void setJjr(String jjr) {
        this.jjr = jjr;
    }

    @Basic
    @Column(name = "JJSJ", nullable = true)
    public Date getJjsj() {
        return jjsj;
    }

    public void setJjsj(Date jjsj) {
        this.jjsj = jjsj;
    }

    @Basic
    @Column(name = "JJRQM", nullable = true, length = 155)
    public String getJjrqm() {
        return jjrqm;
    }

    public void setJjrqm(String jjrqm) {
        this.jjrqm = jjrqm;
    }

    @Basic
    @Column(name = "ITEM_GROUP", nullable = true, precision = 0)
    public Integer getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(Integer itemGroup) {
        this.itemGroup = itemGroup;
    }

    @Basic
    @Column(name = "ADICON_CODE", nullable = true, length = 20)
    public String getAdiconCode() {
        return adiconCode;
    }

    public void setAdiconCode(String adiconCode) {
        this.adiconCode = adiconCode;
    }

    @Basic
    @Column(name = "CHARGE_ID", nullable = true, length = 32)
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    @Basic
    @Column(name = "TRADE_NO", nullable = true, length = 32)
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peispatientfeeitem that = (Peispatientfeeitem) o;
        return Objects.equals(idPatientfeeitem, that.idPatientfeeitem) && Objects.equals(idPatient, that.idPatient) && Objects.equals(idExamfeeitem, that.idExamfeeitem) && Objects.equals(examfeeitemName, that.examfeeitemName) && Objects.equals(price, that.price) && Objects.equals(factprice, that.factprice) && Objects.equals(settleprice, that.settleprice) && Objects.equals(fAddeditem, that.fAddeditem) && Objects.equals(idPayway, that.idPayway) && Objects.equals(fRegistered, that.fRegistered) && Objects.equals(idDoctorreg, that.idDoctorreg) && Objects.equals(doctorregR, that.doctorregR) && Objects.equals(registerTime, that.registerTime) && Objects.equals(idPatientregistersheet, that.idPatientregistersheet) && Objects.equals(fRegreturned, that.fRegreturned) && Objects.equals(fFeecharged, that.fFeecharged) && Objects.equals(idFeecharger, that.idFeecharger) && Objects.equals(feechargerNameR, that.feechargerNameR) && Objects.equals(feechargeTime, that.feechargeTime) && Objects.equals(idFeediscounter, that.idFeediscounter) && Objects.equals(batchnumber, that.batchnumber) && Objects.equals(tubeposition, that.tubeposition) && Objects.equals(samplenumber, that.samplenumber) && Objects.equals(fLabsampled, that.fLabsampled) && Objects.equals(idLabsampler, that.idLabsampler) && Objects.equals(labsamplerNameR, that.labsamplerNameR) && Objects.equals(labsampleTime, that.labsampleTime) && Objects.equals(fLabsendtolis, that.fLabsendtolis) && Objects.equals(labsendtolisTime, that.labsendtolisTime) && Objects.equals(fLabrcvdfromlis, that.fLabrcvdfromlis) && Objects.equals(labrcvdfromlisTime, that.labrcvdfromlisTime) && Objects.equals(fGiveup, that.fGiveup) && Objects.equals(fExaminated, that.fExaminated) && Objects.equals(idPatientexamdepart, that.idPatientexamdepart) && Objects.equals(idExamdoctor, that.idExamdoctor) && Objects.equals(examdoctorNameR, that.examdoctorNameR) && Objects.equals(examinateTime, that.examinateTime) && Objects.equals(fMarkFeereturn, that.fMarkFeereturn) && Objects.equals(fWorkInnerModify, that.fWorkInnerModify) && Objects.equals(severedegree, that.severedegree) && Objects.equals(positivesummary, that.positivesummary) && Objects.equals(interfacemarks, that.interfacemarks) && Objects.equals(urlresult, that.urlresult) && Objects.equals(fDelayed, that.fDelayed) && Objects.equals(dtDelayedtill, that.dtDelayedtill) && Objects.equals(notewhydelayed, that.notewhydelayed) && Objects.equals(idExamplace, that.idExamplace) && Objects.equals(fTransferedhl7, that.fTransferedhl7) && Objects.equals(qty, that.qty) && Objects.equals(feeitemdesc, that.feeitemdesc) && Objects.equals(feeitemsummary, that.feeitemsummary) && Objects.equals(idTypist, that.idTypist) && Objects.equals(idExamapprovedby, that.idExamapprovedby) && Objects.equals(examapprovedbyNameR, that.examapprovedbyNameR) && Objects.equals(samplenumberfromlis, that.samplenumberfromlis) && Objects.equals(samplemsgfromlis, that.samplemsgfromlis) && Objects.equals(receiptsheetnofromhis, that.receiptsheetnofromhis) && Objects.equals(feeitemrequestno, that.feeitemrequestno) && Objects.equals(samplestatus, that.samplestatus) && Objects.equals(backupsingleitemcopiesprinted, that.backupsingleitemcopiesprinted) && Objects.equals(fFeechargedinttrans, that.fFeechargedinttrans) && Objects.equals(giveupnotelet, that.giveupnotelet) && Objects.equals(createDate, that.createDate) && Objects.equals(modifyDate, that.modifyDate) && Objects.equals(sfjx, that.sfjx) && Objects.equals(jxys, that.jxys) && Objects.equals(idKs, that.idKs) && Objects.equals(count, that.count) && Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(changeItem, that.changeItem) && Objects.equals(isMintc, that.isMintc) && Objects.equals(isbx, that.isbx) && Objects.equals(bxcount, that.bxcount) && Objects.equals(endtuiprice, that.endtuiprice) && Objects.equals(actualprice, that.actualprice) && Objects.equals(shortCode, that.shortCode) && Objects.equals(qjr, that.qjr) && Objects.equals(bjr, that.bjr) && Objects.equals(cjr, that.cjr) && Objects.equals(qjsj, that.qjsj) && Objects.equals(bjsj, that.bjsj) && Objects.equals(cjsj, that.cjsj) && Objects.equals(sfjj, that.sfjj) && Objects.equals(jjr, that.jjr) && Objects.equals(jjsj, that.jjsj) && Objects.equals(jjrqm, that.jjrqm) && Objects.equals(itemGroup, that.itemGroup) && Objects.equals(adiconCode, that.adiconCode) && Objects.equals(chargeId, that.chargeId) && Objects.equals(tradeNo, that.tradeNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPatientfeeitem, idPatient, idExamfeeitem, examfeeitemName, price, factprice, settleprice, fAddeditem, idPayway, fRegistered, idDoctorreg, doctorregR, registerTime, idPatientregistersheet, fRegreturned, fFeecharged, idFeecharger, feechargerNameR, feechargeTime, idFeediscounter, batchnumber, tubeposition, samplenumber, fLabsampled, idLabsampler, labsamplerNameR, labsampleTime, fLabsendtolis, labsendtolisTime, fLabrcvdfromlis, labrcvdfromlisTime, fGiveup, fExaminated, idPatientexamdepart, idExamdoctor, examdoctorNameR, examinateTime, fMarkFeereturn, fWorkInnerModify, severedegree, positivesummary, interfacemarks, urlresult, fDelayed, dtDelayedtill, notewhydelayed, idExamplace, fTransferedhl7, qty, feeitemdesc, feeitemsummary, idTypist, idExamapprovedby, examapprovedbyNameR, samplenumberfromlis, samplemsgfromlis, receiptsheetnofromhis, feeitemrequestno, samplestatus, backupsingleitemcopiesprinted, fFeechargedinttrans, giveupnotelet, createDate, modifyDate, sfjx, jxys, idKs, count, id, createdate, modifydate, changeItem, isMintc, isbx, bxcount, endtuiprice, actualprice, shortCode, qjr, bjr, cjr, qjsj, bjsj, cjsj, sfjj, jjr, jjsj, jjrqm, itemGroup, adiconCode, chargeId, tradeNo);
    }
}