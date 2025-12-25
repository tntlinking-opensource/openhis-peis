package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_BASE_INDUSTRY")
public class BaseIndustry {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String category;
    private String major;
    private String middle;
    private String sub;
    private Integer arrangement;
    private String interfaceCode;
    private Integer isDelete;
    private String industryName;
    private String illustration;
    private String industryCode;
    private Integer idx;

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
    @Column(name = "CATEGORY", nullable = true, length = 2)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "MAJOR", nullable = true, length = 4)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Basic
    @Column(name = "MIDDLE", nullable = true, length = 6)
    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    @Basic
    @Column(name = "SUB", nullable = true, length = 8)
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    @Basic
    @Column(name = "ARRANGEMENT", nullable = true, precision = 0)
    public Integer getArrangement() {
        return arrangement;
    }

    public void setArrangement(Integer arrangement) {
        this.arrangement = arrangement;
    }

    @Basic
    @Column(name = "INTERFACE_CODE", nullable = true, length = 8)
    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    @Basic
    @Column(name = "IS_DELETE", nullable = false, precision = 0)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "INDUSTRY_NAME", nullable = false, length = 90)
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @Basic
    @Column(name = "ILLUSTRATION", nullable = true, length = 900)
    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    @Basic
    @Column(name = "INDUSTRY_CODE", nullable = true, length = 15)
    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    @Basic
    @Column(name = "IDX", nullable = true, precision = 0)
    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseIndustry that = (BaseIndustry) o;
        return isDelete == that.isDelete && Objects.equals(id, that.id) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(category, that.category) && Objects.equals(major, that.major) && Objects.equals(middle, that.middle) && Objects.equals(sub, that.sub) && Objects.equals(arrangement, that.arrangement) && Objects.equals(interfaceCode, that.interfaceCode) && Objects.equals(industryName, that.industryName) && Objects.equals(illustration, that.illustration) && Objects.equals(industryCode, that.industryCode) && Objects.equals(idx, that.idx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, category, major, middle, sub, arrangement, interfaceCode, isDelete, industryName, illustration, industryCode, idx);
    }
}