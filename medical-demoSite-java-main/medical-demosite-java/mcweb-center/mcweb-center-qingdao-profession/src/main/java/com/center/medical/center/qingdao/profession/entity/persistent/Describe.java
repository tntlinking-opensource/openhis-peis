package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;

@Entity
@Table(name = "MD_DESCRIBE")
public class Describe {
    private String id;
    private String itemId;//检查项目ID
    private String patientcode;
    private String signList;//检查结果
    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Basic
    @Column(name = "PATIENTCODE", nullable = false, length = 32)
    public String getPatientcode() {
        return patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }
    @Basic
    @Column(name = "SIGN_LIST", nullable = true)
    public String getSignList() {
        return signList;
    }

    public void setSignList(String signList) {
        this.signList = signList;
    }
    @Basic
    @Column(name = "ITEM_ID", nullable = false, length = 32)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
