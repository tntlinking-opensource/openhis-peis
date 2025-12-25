package com.center.medical.center.qingdao.profession.entity.persistent;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MD_BASE_ZONE_QD")
@Data
public class BaseZoneQd {
    private String id;
    private Date createdate;
    private Date modifydate;
    private String zoneCode;
    private String zoneName;
    private String qingdaoZoneCode;
    private String qingdaoZoneName;

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
    @Column(name = "QINGDAO_ZONE_CODE")
    public String getQingdaoZoneCode() {
        return qingdaoZoneCode;
    }

    public void setQingdaoZoneCode(String qingdaoZoneCode) {
        this.qingdaoZoneCode = qingdaoZoneCode;
    }

    @Basic
    @Column(name = "QINGDAO_ZONE_NAME")
    public String getQingdaoZoneName() {
        return qingdaoZoneName;
    }

    public void setQingdaoZoneName(String qingdaoZoneName) {
        this.qingdaoZoneName = qingdaoZoneName;
    }
}
