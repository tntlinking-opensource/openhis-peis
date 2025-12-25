package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MD_OCCUPATION_SYMPTOM")
public class OccupationSymptom {
    private String id;
    private String symptomCode;
    private String symptom;
    private String inputCode;
    private String dbUser;
    private Time createdate;
    private Time modifydate;
    private Long isDelete;

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SYMPTOM_CODE", nullable = false, length = 10)
    public String getSymptomCode() {
        return symptomCode;
    }

    public void setSymptomCode(String symptomCode) {
        this.symptomCode = symptomCode;
    }

    @Basic
    @Column(name = "SYMPTOM", nullable = true, length = 50)
    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    @Basic
    @Column(name = "INPUT_CODE", nullable = true, length = 50)
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Basic
    @Column(name = "DB_USER", nullable = true, length = 10)
    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
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
    @Column(name = "IS_DELETE", nullable = true, precision = 0)
    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OccupationSymptom that = (OccupationSymptom) o;
        return Objects.equals(id, that.id) && Objects.equals(symptomCode, that.symptomCode) && Objects.equals(symptom, that.symptom) && Objects.equals(inputCode, that.inputCode) && Objects.equals(dbUser, that.dbUser) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symptomCode, symptom, inputCode, dbUser, createdate, modifydate, isDelete);
    }
}