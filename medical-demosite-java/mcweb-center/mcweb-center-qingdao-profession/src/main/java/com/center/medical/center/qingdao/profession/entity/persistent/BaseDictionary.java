package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BASE_DICTIONARY")
public class BaseDictionary {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String dictionaryType;
    private String dictionaryCode;
    private String dictionaryName;
    private Integer isDelete;
    private String medicalCode;
    private String shandongCode;
    private String qingdaoCode;

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
    @Column(name = "MODIFYDATE", nullable = true)
    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "DICTIONARY_TYPE", nullable = false, length = 30)
    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    @Basic
    @Column(name = "DICTIONARY_CODE", nullable = false, length = 30)
    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    @Basic
    @Column(name = "DICTIONARY_NAME", nullable = false, length = 200)
    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @Basic
    @Column(name = "IS_DELETE", nullable = true, precision = 0)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "MEDICAL_CODE", nullable = true, length = 32)
    public String getMedicalCode() {
        return medicalCode;
    }

    public void setMedicalCode(String medicalCode) {
        this.medicalCode = medicalCode;
    }

    @Basic
    @Column(name = "SHANDONG_CODE", nullable = true, length = 32)
    public String getShandongCode() {
        return shandongCode;
    }

    public void setShandongCode(String shandongCode) {
        this.shandongCode = shandongCode;
    }

    @Basic
    @Column(name = "QINGDAO_CODE", nullable = true, length = 32)
    public String getQingdaoCode() {
        return qingdaoCode;
    }

    public void setQingdaoCode(String qingdaoCode) {
        this.qingdaoCode = qingdaoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDictionary that = (BaseDictionary) o;
        return Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(dictionaryType, that.dictionaryType) && Objects.equals(dictionaryCode, that.dictionaryCode) && Objects.equals(dictionaryName, that.dictionaryName) && Objects.equals(isDelete, that.isDelete) && Objects.equals(medicalCode, that.medicalCode) && Objects.equals(shandongCode, that.shandongCode) && Objects.equals(qingdaoCode, that.qingdaoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, dictionaryType, dictionaryCode, dictionaryName, isDelete, medicalCode, shandongCode, qingdaoCode);
    }
}