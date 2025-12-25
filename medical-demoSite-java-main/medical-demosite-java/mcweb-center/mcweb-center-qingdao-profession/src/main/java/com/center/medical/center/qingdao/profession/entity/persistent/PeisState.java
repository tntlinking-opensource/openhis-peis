package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_PEIS_STATE")
public class PeisState {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String patientcode;
    private Integer fDiffperson;
    private Integer fInneroper;
    private String patientflag;
    private Integer idPatientclass2;
    private Integer idGuidancereturnedby;
    private Integer scbs;
    private Integer countreportpdf;
    private Integer countreportcomparexml;
    private Integer isBatchRegistered;
    private Integer jinanStatus;
    private String jinanMsg;
    private Date uploadDate;
    @Id
    @Column(name = "ID", nullable = false, length = 32)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
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
    @Column(name = "F_DIFFPERSON", nullable = false, precision = 0)
    public Integer getfDiffperson() {
        return fDiffperson;
    }

    public void setfDiffperson(Integer fDiffperson) {
        this.fDiffperson = fDiffperson;
    }

    @Basic
    @Column(name = "F_INNEROPER", nullable = false, precision = 0)
    public Integer getfInneroper() {
        return fInneroper;
    }

    public void setfInneroper(Integer fInneroper) {
        this.fInneroper = fInneroper;
    }

    @Basic
    @Column(name = "PATIENTFLAG", nullable = false, length = 3)
    public String getPatientflag() {
        return patientflag;
    }

    public void setPatientflag(String patientflag) {
        this.patientflag = patientflag;
    }

    @Basic
    @Column(name = "ID_PATIENTCLASS2", nullable = false, precision = 0)
    public Integer getIdPatientclass2() {
        return idPatientclass2;
    }

    public void setIdPatientclass2(Integer idPatientclass2) {
        this.idPatientclass2 = idPatientclass2;
    }

    @Basic
    @Column(name = "ID_GUIDANCERETURNEDBY", nullable = false, precision = 0)
    public Integer getIdGuidancereturnedby() {
        return idGuidancereturnedby;
    }

    public void setIdGuidancereturnedby(Integer idGuidancereturnedby) {
        this.idGuidancereturnedby = idGuidancereturnedby;
    }

    @Basic
    @Column(name = "SCBS", nullable = false, precision = 0)
    public Integer getScbs() {
        return scbs;
    }

    public void setScbs(Integer scbs) {
        this.scbs = scbs;
    }

    @Basic
    @Column(name = "COUNTREPORTPDF", nullable = false, precision = 0)
    public Integer getCountreportpdf() {
        return countreportpdf;
    }

    public void setCountreportpdf(Integer countreportpdf) {
        this.countreportpdf = countreportpdf;
    }

    @Basic
    @Column(name = "COUNTREPORTCOMPAREXML", nullable = false, precision = 0)
    public Integer getCountreportcomparexml() {
        return countreportcomparexml;
    }

    public void setCountreportcomparexml(Integer countreportcomparexml) {
        this.countreportcomparexml = countreportcomparexml;
    }

    @Basic
    @Column(name = "IS_BATCH_REGISTERED", nullable = false, precision = 0)
    public Integer isBatchRegistered() {
        return isBatchRegistered;
    }

    public void setBatchRegistered(Integer batchRegistered) {
        isBatchRegistered = batchRegistered;
    }

    @Basic
    @Column(name = "JINAN_STATUS", nullable = true, precision = 0)
    public Integer getJinanStatus() {
        return jinanStatus;
    }

    public void setJinanStatus(Integer jinanStatus) {
        this.jinanStatus = jinanStatus;
    }

    @Basic
    @Column(name = "JINAN_MSG", nullable = true, length = 300)
    public String getJinanMsg() {
        return jinanMsg;
    }

    public void setJinanMsg(String jinanMsg) {
        this.jinanMsg = jinanMsg;
    }

    @Basic
    @Column(name = "UPLOAD_DATE")
    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeisState peisState = (PeisState) o;
        return fDiffperson == peisState.fDiffperson && fInneroper == peisState.fInneroper && idPatientclass2 == peisState.idPatientclass2 && idGuidancereturnedby == peisState.idGuidancereturnedby && scbs == peisState.scbs && countreportpdf == peisState.countreportpdf && countreportcomparexml == peisState.countreportcomparexml && isBatchRegistered == peisState.isBatchRegistered && Objects.equals(id, peisState.id) && Objects.equals(createdate, peisState.createdate) && Objects.equals(modifydate, peisState.modifydate) && Objects.equals(patientcode, peisState.patientcode) && Objects.equals(patientflag, peisState.patientflag) && Objects.equals(jinanStatus, peisState.jinanStatus) && Objects.equals(jinanMsg, peisState.jinanMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, patientcode, fDiffperson, fInneroper, patientflag, idPatientclass2, idGuidancereturnedby, scbs, countreportpdf, countreportcomparexml, isBatchRegistered, jinanStatus, jinanMsg);
    }
}