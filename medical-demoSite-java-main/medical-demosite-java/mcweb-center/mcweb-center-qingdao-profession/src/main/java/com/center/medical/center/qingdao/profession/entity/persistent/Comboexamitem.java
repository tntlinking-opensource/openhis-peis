package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MD_COMBOEXAMITEM")
public class Comboexamitem {
    private String harmId;
    private String examId;
    private String itemId;
    private String id;
    private Time createdate;
    private Time modifydate;
    private Long scopeUpper;
    private Long scoperFloor;
    private Long gscopeupper;
    private Long gscoperfloor;
    private String comboId;
    private String medicalType;
    private String ksId;

    @Basic
    @Column(name = "HARM_ID", nullable = true, length = 32)
    public String getHarmId() {
        return harmId;
    }

    public void setHarmId(String harmId) {
        this.harmId = harmId;
    }

    @Basic
    @Column(name = "EXAM_ID", nullable = true, length = 32)
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    @Basic
    @Column(name = "ITEM_ID", nullable = true, length = 32)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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
    @Column(name = "SCOPE_UPPER", nullable = true, precision = 3)
    public Long getScopeUpper() {
        return scopeUpper;
    }

    public void setScopeUpper(Long scopeUpper) {
        this.scopeUpper = scopeUpper;
    }

    @Basic
    @Column(name = "SCOPER_FLOOR", nullable = true, precision = 3)
    public Long getScoperFloor() {
        return scoperFloor;
    }

    public void setScoperFloor(Long scoperFloor) {
        this.scoperFloor = scoperFloor;
    }

    @Basic
    @Column(name = "GSCOPEUPPER", nullable = true, precision = 3)
    public Long getGscopeupper() {
        return gscopeupper;
    }

    public void setGscopeupper(Long gscopeupper) {
        this.gscopeupper = gscopeupper;
    }

    @Basic
    @Column(name = "GSCOPERFLOOR", nullable = true, precision = 3)
    public Long getGscoperfloor() {
        return gscoperfloor;
    }

    public void setGscoperfloor(Long gscoperfloor) {
        this.gscoperfloor = gscoperfloor;
    }

    @Basic
    @Column(name = "COMBO_ID", nullable = true, length = 32)
    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    @Basic
    @Column(name = "MEDICAL_TYPE", nullable = true, length = 1)
    public String getMedicalType() {
        return medicalType;
    }

    public void setMedicalType(String medicalType) {
        this.medicalType = medicalType;
    }

    @Basic
    @Column(name = "KS_ID", nullable = true, length = 32)
    public String getKsId() {
        return ksId;
    }

    public void setKsId(String ksId) {
        this.ksId = ksId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comboexamitem that = (Comboexamitem) o;
        return Objects.equals(harmId, that.harmId) && Objects.equals(examId, that.examId) && Objects.equals(itemId, that.itemId) && Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(scopeUpper, that.scopeUpper) && Objects.equals(scoperFloor, that.scoperFloor) && Objects.equals(gscopeupper, that.gscopeupper) && Objects.equals(gscoperfloor, that.gscoperfloor) && Objects.equals(comboId, that.comboId) && Objects.equals(medicalType, that.medicalType) && Objects.equals(ksId, that.ksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(harmId, examId, itemId, id, createdate, modifydate, scopeUpper, scoperFloor, gscopeupper, gscoperfloor, comboId, medicalType, ksId);
    }
}