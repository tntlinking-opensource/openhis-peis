package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_HARM")
public class Harm {
    private String id;
    private String harmCode;
    private String harmName;
    private String harmClass;
    private String inputCode;
    private String dbUser;
    private String keyword;
    private String diagnosisFrom;
    private String mbjbZyb;
    private String mbjbJjz;
    private String influence;
    private Date createdate;
    private Date modifydate;
    private String diagnosis;
    private String industrialDisease;
    private String contraindication;
    private String affect;
    private String classId;
    private Integer isDelete;
    private String note;
    private Boolean isPublic;
    private String fzxIds;
    private String creater;
    private String createFzx;

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
    @Column(name = "HARM_CODE", nullable = true, length = 32)
    public String getHarmCode() {
        return harmCode;
    }

    public void setHarmCode(String harmCode) {
        this.harmCode = harmCode;
    }

    @Basic
    @Column(name = "HARM_NAME", nullable = true, length = 60)
    public String getHarmName() {
        return harmName;
    }

    public void setHarmName(String harmName) {
        this.harmName = harmName;
    }

    @Basic
    @Column(name = "HARM_CLASS", nullable = true, length = 32)
    public String getHarmClass() {
        return harmClass;
    }

    public void setHarmClass(String harmClass) {
        this.harmClass = harmClass;
    }

    @Basic
    @Column(name = "INPUT_CODE", nullable = true, length = 32)
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Basic
    @Column(name = "DB_USER", nullable = true, length = 32)
    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Basic
    @Column(name = "KEYWORD", nullable = true, length = 250)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Basic
    @Column(name = "DIAGNOSIS_FROM", nullable = true, length = 200)
    public String getDiagnosisFrom() {
        return diagnosisFrom;
    }

    public void setDiagnosisFrom(String diagnosisFrom) {
        this.diagnosisFrom = diagnosisFrom;
    }

    @Basic
    @Column(name = "MBJB_ZYB", nullable = true, length = 250)
    public String getMbjbZyb() {
        return mbjbZyb;
    }

    public void setMbjbZyb(String mbjbZyb) {
        this.mbjbZyb = mbjbZyb;
    }

    @Basic
    @Column(name = "MBJB_JJZ", nullable = true, length = 250)
    public String getMbjbJjz() {
        return mbjbJjz;
    }

    public void setMbjbJjz(String mbjbJjz) {
        this.mbjbJjz = mbjbJjz;
    }

    @Basic
    @Column(name = "INFLUENCE", nullable = true, length = 1000)
    public String getInfluence() {
        return influence;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
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
    @Column(name = "DIAGNOSIS", nullable = true, length = 250)
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Basic
    @Column(name = "INDUSTRIAL_DISEASE", nullable = true, length = 32)
    public String getIndustrialDisease() {
        return industrialDisease;
    }

    public void setIndustrialDisease(String industrialDisease) {
        this.industrialDisease = industrialDisease;
    }

    @Basic
    @Column(name = "CONTRAINDICATION", nullable = true, length = 32)
    public String getContraindication() {
        return contraindication;
    }

    public void setContraindication(String contraindication) {
        this.contraindication = contraindication;
    }

    @Basic
    @Column(name = "AFFECT", nullable = true, length = 2000)
    public String getAffect() {
        return affect;
    }

    public void setAffect(String affect) {
        this.affect = affect;
    }

    @Basic
    @Column(name = "CLASS_ID", nullable = true, length = 32)
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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
    @Column(name = "NOTE", nullable = true, length = 200)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "IS_PUBLIC", nullable = true, precision = 0)
    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Basic
    @Column(name = "FZX_IDS", nullable = true, length = 1000)
    public String getFzxIds() {
        return fzxIds;
    }

    public void setFzxIds(String fzxIds) {
        this.fzxIds = fzxIds;
    }

    @Basic
    @Column(name = "CREATER", nullable = true, length = 255)
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Basic
    @Column(name = "CREATE_FZX", nullable = true, length = 600)
    public String getCreateFzx() {
        return createFzx;
    }

    public void setCreateFzx(String createFzx) {
        this.createFzx = createFzx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Harm harm = (Harm) o;
        return Objects.equals(id, harm.id) && Objects.equals(harmCode, harm.harmCode) && Objects.equals(harmName, harm.harmName) && Objects.equals(harmClass, harm.harmClass) && Objects.equals(inputCode, harm.inputCode) && Objects.equals(dbUser, harm.dbUser) && Objects.equals(keyword, harm.keyword) && Objects.equals(diagnosisFrom, harm.diagnosisFrom) && Objects.equals(mbjbZyb, harm.mbjbZyb) && Objects.equals(mbjbJjz, harm.mbjbJjz) && Objects.equals(influence, harm.influence) && Objects.equals(createdate, harm.createdate) && Objects.equals(modifydate, harm.modifydate) && Objects.equals(diagnosis, harm.diagnosis) && Objects.equals(industrialDisease, harm.industrialDisease) && Objects.equals(contraindication, harm.contraindication) && Objects.equals(affect, harm.affect) && Objects.equals(classId, harm.classId) && Objects.equals(isDelete, harm.isDelete) && Objects.equals(note, harm.note) && Objects.equals(isPublic, harm.isPublic) && Objects.equals(fzxIds, harm.fzxIds) && Objects.equals(creater, harm.creater) && Objects.equals(createFzx, harm.createFzx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, harmCode, harmName, harmClass, inputCode, dbUser, keyword, diagnosisFrom, mbjbZyb, mbjbJjz, influence, createdate, modifydate, diagnosis, industrialDisease, contraindication, affect, classId, isDelete, note, isPublic, fzxIds, creater, createFzx);
    }
}