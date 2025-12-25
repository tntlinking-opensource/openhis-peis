package com.center.medical.center.qingdao.profession.entity.persistent;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_PEISPATIENT_CONSULTATION")
public class PeispatientConsultation {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String patientcode;
    private String everOfDisease;
    private String ccnl;
    private String jq;
    private String zq;
    private String tjnl;
    private String familyNumber;
    private String zc;
    private String sc;
    private String lc;
    private String jt;
    private String ywrc;
    private String abstainSmokeNote;
    private String everydaySmokeN;
    private String smokeYear;
    private String noKissTheCup;
    private String betweenKissTheCup;
    private String evermoreKiss;
    private String abstainLostKiss;
    private String kissYearN;
    private String kissAmount;
    private String kissType;
    private String familyOfDisease;
    private String symptom;
    private Integer isAudit;
    private String everOfDiseaseRemark;
    private String kissMonth;
    private String smokeMonth;

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CREATEDATE", nullable = false)
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE", nullable = false)
    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "PATIENTCODE", nullable = false, length = 50)
    public String getPatientcode() {
        return patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }

    @Basic
    @Column(name = "EVER_OF_DISEASE", nullable = true, length = 250)
    public String getEverOfDisease() {
        return everOfDisease;
    }

    public void setEverOfDisease(String everOfDisease) {
        this.everOfDisease = everOfDisease;
    }

    @Basic
    @Column(name = "CCNL", nullable = true, length = 10)
    public String getCcnl() {
        return ccnl;
    }

    public void setCcnl(String ccnl) {
        this.ccnl = ccnl;
    }

    @Basic
    @Column(name = "JQ", nullable = true, length = 10)
    public String getJq() {
        return jq;
    }

    public void setJq(String jq) {
        this.jq = jq;
    }

    @Basic
    @Column(name = "ZQ", nullable = true, length = 10)
    public String getZq() {
        return zq;
    }

    public void setZq(String zq) {
        this.zq = zq;
    }

    @Basic
    @Column(name = "TJNL", nullable = true, length = 10)
    public String getTjnl() {
        return tjnl;
    }

    public void setTjnl(String tjnl) {
        this.tjnl = tjnl;
    }

    @Basic
    @Column(name = "FAMILY_NUMBER", nullable = true, length = 4)
    public String getFamilyNumber() {
        return familyNumber;
    }

    public void setFamilyNumber(String familyNumber) {
        this.familyNumber = familyNumber;
    }

    @Basic
    @Column(name = "ZC", nullable = true, length = 4)
    public String getZc() {
        return zc;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }

    @Basic
    @Column(name = "SC", nullable = true, length = 4)
    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    @Basic
    @Column(name = "LC", nullable = true, length = 4)
    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    @Basic
    @Column(name = "JT", nullable = true, length = 4)
    public String getJt() {
        return jt;
    }

    public void setJt(String jt) {
        this.jt = jt;
    }

    @Basic
    @Column(name = "YWRC", nullable = true, length = 4)
    public String getYwrc() {
        return ywrc;
    }

    public void setYwrc(String ywrc) {
        this.ywrc = ywrc;
    }

    @Basic
    @Column(name = "ABSTAIN_SMOKE_NOTE", nullable = true, length = 10)
    public String getAbstainSmokeNote() {
        return abstainSmokeNote;
    }

    public void setAbstainSmokeNote(String abstainSmokeNote) {
        this.abstainSmokeNote = abstainSmokeNote;
    }

    @Basic
    @Column(name = "EVERYDAY_SMOKE_N", nullable = true, length = 10)
    public String getEverydaySmokeN() {
        return everydaySmokeN;
    }

    public void setEverydaySmokeN(String everydaySmokeN) {
        this.everydaySmokeN = everydaySmokeN;
    }

    @Basic
    @Column(name = "SMOKE_YEAR", nullable = true, length = 10)
    public String getSmokeYear() {
        return smokeYear;
    }

    public void setSmokeYear(String smokeYear) {
        this.smokeYear = smokeYear;
    }

    @Basic
    @Column(name = "NO_KISS_THE_CUP", nullable = true, length = 30)
    public String getNoKissTheCup() {
        return noKissTheCup;
    }

    public void setNoKissTheCup(String noKissTheCup) {
        this.noKissTheCup = noKissTheCup;
    }

    @Basic
    @Column(name = "BETWEEN_KISS_THE_CUP", nullable = true, length = 30)
    public String getBetweenKissTheCup() {
        return betweenKissTheCup;
    }

    public void setBetweenKissTheCup(String betweenKissTheCup) {
        this.betweenKissTheCup = betweenKissTheCup;
    }

    @Basic
    @Column(name = "EVERMORE_KISS", nullable = true, length = 100)
    public String getEvermoreKiss() {
        return evermoreKiss;
    }

    public void setEvermoreKiss(String evermoreKiss) {
        this.evermoreKiss = evermoreKiss;
    }

    @Basic
    @Column(name = "ABSTAIN_LOST_KISS", nullable = true, length = 100)
    public String getAbstainLostKiss() {
        return abstainLostKiss;
    }

    public void setAbstainLostKiss(String abstainLostKiss) {
        this.abstainLostKiss = abstainLostKiss;
    }

    @Basic
    @Column(name = "KISS_YEAR_N", nullable = true, length = 10)
    public String getKissYearN() {
        return kissYearN;
    }

    public void setKissYearN(String kissYearN) {
        this.kissYearN = kissYearN;
    }

    @Basic
    @Column(name = "KISS_AMOUNT", nullable = true, length = 100)
    public String getKissAmount() {
        return kissAmount;
    }

    public void setKissAmount(String kissAmount) {
        this.kissAmount = kissAmount;
    }

    @Basic
    @Column(name = "KISS_TYPE", nullable = true, length = 20)
    public String getKissType() {
        return kissType;
    }

    public void setKissType(String kissType) {
        this.kissType = kissType;
    }

    @Basic
    @Column(name = "FAMILY_OF_DISEASE", nullable = true, length = 1000)
    public String getFamilyOfDisease() {
        return familyOfDisease;
    }

    public void setFamilyOfDisease(String familyOfDisease) {
        this.familyOfDisease = familyOfDisease;
    }

    @Basic
    @Column(name = "SYMPTOM", nullable = true, length = 1000)
    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    @Basic
    @Column(name = "IS_AUDIT", nullable = true, precision = 0)
    public Integer getAudit() {
        return isAudit;
    }

    public void setAudit(Integer audit) {
        isAudit = audit;
    }

    @Basic
    @Column(name = "EVER_OF_DISEASE_REMARK", nullable = true, length = 1000)
    public String getEverOfDiseaseRemark() {
        return everOfDiseaseRemark;
    }

    @Basic
    @Column(name = "KISS_MONTH", nullable = true)
    public String getKissMonth() {
        return kissMonth;
    }

    public void setKissMonth(String kissMonth) {
        this.kissMonth = kissMonth;
    }

    @Basic
    @Column(name = "SMOKE_MONTH", nullable = true)
    public String getSmokeMonth() {
        return smokeMonth;
    }

    public void setSmokeMonth(String smokeMonth) {
        this.smokeMonth = smokeMonth;
    }

    public void setEverOfDiseaseRemark(String everOfDiseaseRemark) {
        this.everOfDiseaseRemark = everOfDiseaseRemark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeispatientConsultation that = (PeispatientConsultation) o;
        return Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(patientcode, that.patientcode) && Objects.equals(everOfDisease, that.everOfDisease) && Objects.equals(ccnl, that.ccnl) && Objects.equals(jq, that.jq) && Objects.equals(zq, that.zq) && Objects.equals(tjnl, that.tjnl) && Objects.equals(familyNumber, that.familyNumber) && Objects.equals(zc, that.zc) && Objects.equals(sc, that.sc) && Objects.equals(lc, that.lc) && Objects.equals(jt, that.jt) && Objects.equals(ywrc, that.ywrc) && Objects.equals(abstainSmokeNote, that.abstainSmokeNote) && Objects.equals(everydaySmokeN, that.everydaySmokeN) && Objects.equals(smokeYear, that.smokeYear) && Objects.equals(noKissTheCup, that.noKissTheCup) && Objects.equals(betweenKissTheCup, that.betweenKissTheCup) && Objects.equals(evermoreKiss, that.evermoreKiss) && Objects.equals(abstainLostKiss, that.abstainLostKiss) && Objects.equals(kissYearN, that.kissYearN) && Objects.equals(kissAmount, that.kissAmount) && Objects.equals(kissType, that.kissType) && Objects.equals(familyOfDisease, that.familyOfDisease) && Objects.equals(symptom, that.symptom) && Objects.equals(isAudit, that.isAudit) && Objects.equals(everOfDiseaseRemark, that.everOfDiseaseRemark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, patientcode, everOfDisease, ccnl, jq, zq, tjnl, familyNumber, zc, sc, lc, jt, ywrc, abstainSmokeNote, everydaySmokeN, smokeYear, noKissTheCup, betweenKissTheCup, evermoreKiss, abstainLostKiss, kissYearN, kissAmount, kissType, familyOfDisease, symptom, isAudit, everOfDiseaseRemark);
    }
}