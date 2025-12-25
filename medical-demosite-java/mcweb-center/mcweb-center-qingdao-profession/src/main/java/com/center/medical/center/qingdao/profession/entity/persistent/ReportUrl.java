package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MD_REPORT_URL")
public class ReportUrl {
    private String id;
    private String depId;
    private String patientcode;
    private String wordUrl;
    private Time createdate;
    private Time modifydate;
    private Boolean isHead;
    private String pdfUrl;
    private String depName;
    private Boolean diseaseHealth;
    private String pdfUrlHead;
    private String wordUrlHead;
    private Long shortCode;
    private String configId;

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DEP_ID", nullable = true, length = 32)
    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    @Basic
    @Column(name = "PATIENTCODE", nullable = true, length = 12)
    public String getPatientcode() {
        return patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }

    @Basic
    @Column(name = "WORD_URL", nullable = true, length = 200)
    public String getWordUrl() {
        return wordUrl;
    }

    public void setWordUrl(String wordUrl) {
        this.wordUrl = wordUrl;
    }

    @Basic
    @Column(name = "CREATEDATE", nullable = true)
    public Time getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Time createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE", nullable = true)
    public Time getModifydate() {
        return modifydate;
    }

    public void setModifydate(Time modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "IS_HEAD", nullable = true, precision = 0)
    public Boolean getHead() {
        return isHead;
    }

    public void setHead(Boolean head) {
        isHead = head;
    }

    @Basic
    @Column(name = "PDF_URL", nullable = true, length = 200)
    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Basic
    @Column(name = "DEP_NAME", nullable = true, length = 50)
    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    @Basic
    @Column(name = "DISEASE_HEALTH", nullable = true, precision = 0)
    public Boolean getDiseaseHealth() {
        return diseaseHealth;
    }

    public void setDiseaseHealth(Boolean diseaseHealth) {
        this.diseaseHealth = diseaseHealth;
    }

    @Basic
    @Column(name = "PDF_URL_HEAD", nullable = true, length = 200)
    public String getPdfUrlHead() {
        return pdfUrlHead;
    }

    public void setPdfUrlHead(String pdfUrlHead) {
        this.pdfUrlHead = pdfUrlHead;
    }

    @Basic
    @Column(name = "WORD_URL_HEAD", nullable = true, length = 200)
    public String getWordUrlHead() {
        return wordUrlHead;
    }

    public void setWordUrlHead(String wordUrlHead) {
        this.wordUrlHead = wordUrlHead;
    }

    @Basic
    @Column(name = "SHORT_CODE", nullable = true, precision = 0)
    public Long getShortCode() {
        return shortCode;
    }

    public void setShortCode(Long shortCode) {
        this.shortCode = shortCode;
    }

    @Basic
    @Column(name = "CONFIG_ID", nullable = true, length = 32)
    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportUrl reportUrl = (ReportUrl) o;
        return Objects.equals(id, reportUrl.id) && Objects.equals(depId, reportUrl.depId) && Objects.equals(patientcode, reportUrl.patientcode) && Objects.equals(wordUrl, reportUrl.wordUrl) && Objects.equals(createdate, reportUrl.createdate) && Objects.equals(modifydate, reportUrl.modifydate) && Objects.equals(isHead, reportUrl.isHead) && Objects.equals(pdfUrl, reportUrl.pdfUrl) && Objects.equals(depName, reportUrl.depName) && Objects.equals(diseaseHealth, reportUrl.diseaseHealth) && Objects.equals(pdfUrlHead, reportUrl.pdfUrlHead) && Objects.equals(wordUrlHead, reportUrl.wordUrlHead) && Objects.equals(shortCode, reportUrl.shortCode) && Objects.equals(configId, reportUrl.configId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depId, patientcode, wordUrl, createdate, modifydate, isHead, pdfUrl, depName, diseaseHealth, pdfUrlHead, wordUrlHead, shortCode, configId);
    }
}