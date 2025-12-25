package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_BASE_ZONE")
public class BaseZone {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String zoneCode;
    private String zoneName;
    private Integer zoneLevel;
    private String pcode;
    private Integer isDelete;
    private String qingdaoCode;
    private String qingdaoPCode;
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
    @Column(name = "ZONE_CODE", nullable = false, length = 20)
    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    @Basic
    @Column(name = "ZONE_NAME", nullable = false, length = 60)
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    @Basic
    @Column(name = "ZONE_LEVEL", nullable = false, precision = 0)
    public Integer getZoneLevel() {
        return zoneLevel;
    }

    public void setZoneLevel(Integer zoneLevel) {
        this.zoneLevel = zoneLevel;
    }

    @Basic
    @Column(name = "PCODE", nullable = true, length = 20)
    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    @Basic
    @Column(name = "IS_DELETE", nullable = false, precision = 0)
    public Integer isDelete() {
        return isDelete;
    }

    public void setDelete(Integer delete) {
        isDelete = delete;
    }

    @Basic
    @Column(name = "QINGDAO_CODE")
    public String getQingdaoCode() {
        return qingdaoCode;
    }

    public void setQingdaoCode(String qingdaoCode) {
        this.qingdaoCode = qingdaoCode;
    }

    @Basic
    @Column(name = "QINGDAO_PCODE")
    public String getQingdaoPCode() {
        return qingdaoPCode;
    }

    public void setQingdaoPCode(String qingdaoPCode) {
        this.qingdaoPCode = qingdaoPCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseZone baseZone = (BaseZone) o;
        return zoneLevel == baseZone.zoneLevel && isDelete == baseZone.isDelete && Objects.equals(id, baseZone.id) && Objects.equals(createdate, baseZone.createdate) && Objects.equals(modifydate, baseZone.modifydate) && Objects.equals(zoneCode, baseZone.zoneCode) && Objects.equals(zoneName, baseZone.zoneName) && Objects.equals(pcode, baseZone.pcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdate, modifydate, zoneCode, zoneName, zoneLevel, pcode, isDelete);
    }
}