package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "SYS_USER")
public class QxUsers {
    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private Time createdate;
    private Time modifydate;
    private Time logindate;
    private String loginip;
    private String useraccountnonexpired;
    private String useraccountnonlocked;
    private String userenabled;
    private String usercredentialsnonexpired;
    private String depid;
    private String issystem;
    private Long sdiscount;
    private Long ldiscount;
    private String isleader;
    private String cid;
    private Boolean isDelete;
    private String inputCode;
    private String reciveCode;
    private String phone;
    private Boolean isDoc;
    private String userCode;
    private String picture;
    private String signPic;
    private String superiorId;
    private Boolean isbj;
    private String secretPassword;
    private Time expDate;
    private Boolean isLocked;
    private Time lockdate;
    private Time unlockdate;
    private String gwyAccount;

    @Id
    @Column(name = "USER_ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_NAME", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "NICK_NAME", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true)
    public Time getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Time createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    public Time getModifydate() {
        return modifydate;
    }

    public void setModifydate(Time modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "LOGIN_DATE", nullable = true)
    public Time getLogindate() {
        return logindate;
    }

    public void setLogindate(Time logindate) {
        this.logindate = logindate;
    }

    @Basic
    @Column(name = "LOGIN_IP", nullable = true, length = 255)
    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }


    @Basic
    @Column(name = "DEPT_ID", nullable = true, length = 32)
    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    @Basic
    @Column(name = "SDISCOUNT", nullable = true, precision = 4)
    public Long getSdiscount() {
        return sdiscount;
    }

    public void setSdiscount(Long sdiscount) {
        this.sdiscount = sdiscount;
    }

    @Basic
    @Column(name = "LDISCOUNT", nullable = true, precision = 4)
    public Long getLdiscount() {
        return ldiscount;
    }

    public void setLdiscount(Long ldiscount) {
        this.ldiscount = ldiscount;
    }

    @Basic
    @Column(name = "ISLEADER", nullable = true, length = 10)
    public String getIsleader() {
        return isleader;
    }

    public void setIsleader(String isleader) {
        this.isleader = isleader;
    }

    @Basic
    @Column(name = "CID", nullable = true, length = 32)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "DEL_FLAG", nullable = true, precision = 0)
    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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
    @Column(name = "RECIVE_CODE", nullable = true, length = 32)
    public String getReciveCode() {
        return reciveCode;
    }

    public void setReciveCode(String reciveCode) {
        this.reciveCode = reciveCode;
    }

    @Basic
    @Column(name = "PHONENUMBER", nullable = true, length = 255)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "IS_DOC", nullable = true, precision = 0)
    public Boolean getDoc() {
        return isDoc;
    }

    public void setDoc(Boolean doc) {
        isDoc = doc;
    }

    @Basic
    @Column(name = "USER_CODE", nullable = true, length = 40)
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Basic
    @Column(name = "PICTURE", nullable = true)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Basic
    @Column(name = "SIGN_PIC", nullable = true, length = 4000)
    public String getSignPic() {
        return signPic;
    }

    public void setSignPic(String signPic) {
        this.signPic = signPic;
    }

    @Basic
    @Column(name = "SUPERIOR_ID", nullable = true, length = 32)
    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    @Basic
    @Column(name = "ISBJ", nullable = true, precision = 0)
    public Boolean getIsbj() {
        return isbj;
    }

    public void setIsbj(Boolean isbj) {
        this.isbj = isbj;
    }

    @Basic
    @Column(name = "SECRET_PASSWORD", nullable = true, length = 90)
    public String getSecretPassword() {
        return secretPassword;
    }

    public void setSecretPassword(String secretPassword) {
        this.secretPassword = secretPassword;
    }

    @Basic
    @Column(name = "EXP_DATE", nullable = true)
    public Time getExpDate() {
        return expDate;
    }

    public void setExpDate(Time expDate) {
        this.expDate = expDate;
    }

    @Basic
    @Column(name = "IS_LOCKED", nullable = true, precision = 0)
    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    @Basic
    @Column(name = "LOCK_DATE", nullable = true)
    public Time getLockdate() {
        return lockdate;
    }

    public void setLockdate(Time lockdate) {
        this.lockdate = lockdate;
    }

    @Basic
    @Column(name = "UNLOCK_DATE", nullable = true)
    public Time getUnlockdate() {
        return unlockdate;
    }

    public void setUnlockdate(Time unlockdate) {
        this.unlockdate = unlockdate;
    }

    @Basic
//    @Column(name = "GWY_ACCOUNT", nullable = true, length = 255)
    @Transient
    public String getGwyAccount() {
        return gwyAccount;
    }

    public void setGwyAccount(String gwyAccount) {
        this.gwyAccount = gwyAccount;
    }

}