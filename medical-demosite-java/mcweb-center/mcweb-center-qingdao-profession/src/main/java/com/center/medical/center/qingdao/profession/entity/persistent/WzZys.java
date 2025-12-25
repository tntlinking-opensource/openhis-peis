package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_WZ_ZYS")
public class WzZys {
    private String djls;
    private String idPatientarchive;
    private Integer amount;
    private String id;
    private Date createDate;
    private Date startDate;
    private Date stopDate;
    private String dept;
    private String branch;
    private String typeOfWork;
    private String occupationHarm;
    private String localeTestAmount;
    private String defendManage;
    private String occupationBargain;
    private String occupationDefend;
    private String dbUser;
    private String remark;
    private Date createdate;
    private Date modifydate;
    private String createId;
    private String modifyId;
    private String deptId;

    @Basic
    @Column(name = "DJLS")
    public String getDjls() {
        return djls;
    }

    public void setDjls(String djls) {
        this.djls = djls;
    }

    @Basic
    @Column(name = "ID_PATIENTARCHIVE")
    public String getIdPatientarchive() {
        return idPatientarchive;
    }

    public void setIdPatientarchive(String idPatientarchive) {
        this.idPatientarchive = idPatientarchive;
    }

    @Basic
    @Column(name = "AMOUNT")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "STOP_DATE")
    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    @Basic
    @Column(name = "DEPT")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Basic
    @Column(name = "BRANCH")
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Basic
    @Column(name = "TYPE_OF_WORK")
    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    @Basic
    @Column(name = "OCCUPATION_HARM")
    public String getOccupationHarm() {
        return occupationHarm;
    }

    public void setOccupationHarm(String occupationHarm) {
        this.occupationHarm = occupationHarm;
    }

    @Basic
    @Column(name = "LOCALE_TEST_AMOUNT")
    public String getLocaleTestAmount() {
        return localeTestAmount;
    }

    public void setLocaleTestAmount(String localeTestAmount) {
        this.localeTestAmount = localeTestAmount;
    }

    @Basic
    @Column(name = "DEFEND_MANAGE")
    public String getDefendManage() {
        return defendManage;
    }

    public void setDefendManage(String defendManage) {
        this.defendManage = defendManage;
    }

    @Basic
    @Column(name = "OCCUPATION_BARGAIN")
    public String getOccupationBargain() {
        return occupationBargain;
    }

    public void setOccupationBargain(String occupationBargain) {
        this.occupationBargain = occupationBargain;
    }

    @Basic
    @Column(name = "OCCUPATION_DEFEND")
    public String getOccupationDefend() {
        return occupationDefend;
    }

    public void setOccupationDefend(String occupationDefend) {
        this.occupationDefend = occupationDefend;
    }

    @Basic
    @Column(name = "DB_USER")
    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "CREATEDATE")
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE")
    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "CREATE_ID")
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Basic
    @Column(name = "MODIFY_ID")
    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    @Basic
    @Column(name = "DEPT_ID")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WzZys wzZys = (WzZys) o;
        return Objects.equals(djls, wzZys.djls) && Objects.equals(idPatientarchive, wzZys.idPatientarchive) && Objects.equals(amount, wzZys.amount) && Objects.equals(id, wzZys.id) && Objects.equals(createDate, wzZys.createDate) && Objects.equals(startDate, wzZys.startDate) && Objects.equals(stopDate, wzZys.stopDate) && Objects.equals(dept, wzZys.dept) && Objects.equals(branch, wzZys.branch) && Objects.equals(typeOfWork, wzZys.typeOfWork) && Objects.equals(occupationHarm, wzZys.occupationHarm) && Objects.equals(localeTestAmount, wzZys.localeTestAmount) && Objects.equals(defendManage, wzZys.defendManage) && Objects.equals(occupationBargain, wzZys.occupationBargain) && Objects.equals(occupationDefend, wzZys.occupationDefend) && Objects.equals(dbUser, wzZys.dbUser) && Objects.equals(remark, wzZys.remark) && Objects.equals(createdate, wzZys.createdate) && Objects.equals(modifydate, wzZys.modifydate) && Objects.equals(createId, wzZys.createId) && Objects.equals(modifyId, wzZys.modifyId) && Objects.equals(deptId, wzZys.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(djls, idPatientarchive, amount, id, createDate, startDate, stopDate, dept, branch, typeOfWork, occupationHarm, localeTestAmount, defendManage, occupationBargain, occupationDefend, dbUser, remark, createdate, modifydate, createId, modifyId, deptId);
    }
}
