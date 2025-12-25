package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MD_BASE_WORKTYPE")
public class BaseWorktype {
    private String id;
    private Time createdate;
    private Time modifydate;
    private String typeName;
    private String qingdaoCode;
    private String jinanCode;

    private Integer isDelete;

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
    public Time getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Time createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE", nullable = false)
    public Time getModifydate() {
        return modifydate;
    }

    public void setModifydate(Time modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "TYPE_NAME", nullable = false, length = 50)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "QINGDAO_CODE", nullable = false, length = 20)
    public String getQingdaoCode() {
        return qingdaoCode;
    }

    public void setQingdaoCode(String qingdaoCode) {
        this.qingdaoCode = qingdaoCode;
    }

    @Basic
    @Column(name = "JINAN_CODE", nullable = false, length = 25)
    public String getJinanCode() {
        return jinanCode;
    }

    public void setJinanCode(String jinanCode) {
        this.jinanCode = jinanCode;
    }


    @Basic
    @Column(name = "IS_DELETE", nullable = false, length = 25)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseWorktype that = (BaseWorktype) o;
        return Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(typeName, that.typeName) && Objects.equals(qingdaoCode, that.qingdaoCode) && Objects.equals(jinanCode, that.jinanCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, typeName, qingdaoCode, jinanCode);
    }
}