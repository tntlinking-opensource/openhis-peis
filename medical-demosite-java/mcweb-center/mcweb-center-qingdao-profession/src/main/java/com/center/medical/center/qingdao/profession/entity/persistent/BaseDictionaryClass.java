package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "BASE_DICTIONARY_CLASS")
public class BaseDictionaryClass {
    private String id;
    private Date createdate;
    private Integer modifydate;
    private String className;
    private String classCode;
    private Integer isMatchable;

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
    public Integer getModifydate() {
        return modifydate;
    }

    public void setModifydate(Integer modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "CLASS_NAME", nullable = false, length = 60)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "CLASS_CODE", nullable = false, length = 30)
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    @Basic
    @Column(name = "IS_MATCHABLE", nullable = false, precision = 0)
    public Integer getIsMatchable() {
        return isMatchable;
    }

    public void setIsMatchable(Integer isMatchable) {
        this.isMatchable = isMatchable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDictionaryClass that = (BaseDictionaryClass) o;
        return isMatchable == that.isMatchable && Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(className, that.className) && Objects.equals(classCode, that.classCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, className, classCode, isMatchable);
    }
}