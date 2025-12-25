package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BASE_DICTIONARY_MATCH")
public class BaseDictionaryMatch {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String dictionaryId;
    private String medicalId;

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
    @Column(name = "DICTIONARY_ID", nullable = false, length = 32)
    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    @Basic
    @Column(name = "MEDICAL_ID", nullable = false, length = 32)
    public String getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDictionaryMatch that = (BaseDictionaryMatch) o;
        return Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(dictionaryId, that.dictionaryId) && Objects.equals(medicalId, that.medicalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, dictionaryId, medicalId);
    }
}