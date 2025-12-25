package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CRM_SELLCUSTOMER")
public class Sellcustomer {
    private String id;
    private String khdwmc;
    private String khdwsrm;
    private String khdwlxr;
    private String khdh;
    private String frdwmc;
    private String fddbr;
    private String yzbm;
    private String qygm;
    private String qyjjlx;
    private String zywsfzr;
    private String khdwzcdz;
    private String zywsgljg;
    private String dwjgdm;
    private String xsjl;
    private String zclx;
    private String sshy;
    private String lsgx;
    private String sjzgdw;
    private String sjcyrs;
    private String ldrs;
    private String scgrs;
    private String zybwhysrs;
    private String zybwhzycss;
    private String zybwhyslb;
    private String zybwhys;
    private String gylc;
    private String zyyfl;
    private Boolean tjttlx;
    private String zycp;
    private String khsctjdwdz;
    private String bz;
    private Date sjrq;
    private String khzt;
    private Boolean isDelete;
    private String clientid;
    private String ldfpid;
    private String xsjlid;
    private String fzxid;
    private Date createdate;
    private Date modifydate;
    private Boolean lost;
    private Long intId;
    private String briefText;
    private String socialCreditCode;
    private String province;
    private String city;
    private String district;
    private String street;
    private String indusTypeCode;
    private String economyCode;
    private String crptSizeCode;
    private Long workForce;
    private Long holdCardMan;
    private Long workmanNum;
    private Long workmistressNum;
    private String postalcode;
    private String workArea;
    private String registerFund;
    private String safetyPrincipal;
    private Date buildDate;
    private String linkman1;
    private String position1;
    private String linkphone1;
    private String linkman2;
    private String position2;
    private String linkphone2;
    private String safeposition;
    private String safephone;
    private String subjeConn;
    private String enrolAddress;
    private String enrolPostalcode;
    private String occManaOffice;
    private Boolean jinanStatus;
    private String jinanMsg;
    private String indusTypeCode1;
    private String indusTypeCode2;
    private String indusTypeCode3;
    private String phone;
    private String rauKhdwmc;
    private String rauSocialCreditCode;
    private String rauEconomyCode;
    private String rauIndusTypeCode1;
    private String rauIndusTypeCode2;
    private String rauIndusTypeCode3;
    private String rauIndusTypeCode;
    private String rauQygm;
    private String rauProvince;
    private String rauCity;
    private String rauDistrict;
    private String rauStreet;
    private String unitarea;
    private String rauUnitarea;
    private String licenseName;

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "KHDWMC", nullable = true, length = 150)
    public String getKhdwmc() {
        return khdwmc;
    }

    public void setKhdwmc(String khdwmc) {
        this.khdwmc = khdwmc;
    }

    @Basic
    @Column(name = "KHDWSRM", nullable = true, length = 100)
    public String getKhdwsrm() {
        return khdwsrm;
    }

    public void setKhdwsrm(String khdwsrm) {
        this.khdwsrm = khdwsrm;
    }

    @Basic
    @Column(name = "KHDWLXR", nullable = true, length = 200)
    public String getKhdwlxr() {
        return khdwlxr;
    }

    public void setKhdwlxr(String khdwlxr) {
        this.khdwlxr = khdwlxr;
    }

    @Basic
    @Column(name = "KHDH", nullable = true, length = 50)
    public String getKhdh() {
        return khdh;
    }

    public void setKhdh(String khdh) {
        this.khdh = khdh;
    }

    @Basic
    @Column(name = "FRDWMC", nullable = true, length = 500)
    public String getFrdwmc() {
        return frdwmc;
    }

    public void setFrdwmc(String frdwmc) {
        this.frdwmc = frdwmc;
    }

    @Basic
    @Column(name = "FDDBR", nullable = true, length = 25)
    public String getFddbr() {
        return fddbr;
    }

    public void setFddbr(String fddbr) {
        this.fddbr = fddbr;
    }

    @Basic
    @Column(name = "YZBM", nullable = true, length = 6)
    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    @Basic
    @Column(name = "QYGM", nullable = true, length = 5)
    public String getQygm() {
        return qygm;
    }

    public void setQygm(String qygm) {
        this.qygm = qygm;
    }

    @Basic
    @Column(name = "QYJJLX", nullable = true, length = 200)
    public String getQyjjlx() {
        return qyjjlx;
    }

    public void setQyjjlx(String qyjjlx) {
        this.qyjjlx = qyjjlx;
    }

    @Basic
    @Column(name = "ZYWSFZR", nullable = true, length = 20)
    public String getZywsfzr() {
        return zywsfzr;
    }

    public void setZywsfzr(String zywsfzr) {
        this.zywsfzr = zywsfzr;
    }

    @Basic
    @Column(name = "KHDWZCDZ", nullable = true, length = 100)
    public String getKhdwzcdz() {
        return khdwzcdz;
    }

    public void setKhdwzcdz(String khdwzcdz) {
        this.khdwzcdz = khdwzcdz;
    }

    @Basic
    @Column(name = "ZYWSGLJG", nullable = true, length = 50)
    public String getZywsgljg() {
        return zywsgljg;
    }

    public void setZywsgljg(String zywsgljg) {
        this.zywsgljg = zywsgljg;
    }

    @Basic
    @Column(name = "DWJGDM", nullable = true, length = 200)
    public String getDwjgdm() {
        return dwjgdm;
    }

    public void setDwjgdm(String dwjgdm) {
        this.dwjgdm = dwjgdm;
    }

    @Basic
    @Column(name = "XSJL", nullable = true, length = 40)
    public String getXsjl() {
        return xsjl;
    }

    public void setXsjl(String xsjl) {
        this.xsjl = xsjl;
    }

    @Basic
    @Column(name = "ZCLX", nullable = true, length = 5)
    public String getZclx() {
        return zclx;
    }

    public void setZclx(String zclx) {
        this.zclx = zclx;
    }

    @Basic
    @Column(name = "SSHY", nullable = true, length = 200)
    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    @Basic
    @Column(name = "LSGX", nullable = true, length = 200)
    public String getLsgx() {
        return lsgx;
    }

    public void setLsgx(String lsgx) {
        this.lsgx = lsgx;
    }

    @Basic
    @Column(name = "SJZGDW", nullable = true, length = 200)
    public String getSjzgdw() {
        return sjzgdw;
    }

    public void setSjzgdw(String sjzgdw) {
        this.sjzgdw = sjzgdw;
    }

    @Basic
    @Column(name = "SJCYRS", nullable = true, length = 20)
    public String getSjcyrs() {
        return sjcyrs;
    }

    public void setSjcyrs(String sjcyrs) {
        this.sjcyrs = sjcyrs;
    }

    @Basic
    @Column(name = "LDRS", nullable = true, length = 20)
    public String getLdrs() {
        return ldrs;
    }

    public void setLdrs(String ldrs) {
        this.ldrs = ldrs;
    }

    @Basic
    @Column(name = "SCGRS", nullable = true, length = 20)
    public String getScgrs() {
        return scgrs;
    }

    public void setScgrs(String scgrs) {
        this.scgrs = scgrs;
    }

    @Basic
    @Column(name = "ZYBWHYSRS", nullable = true, length = 20)
    public String getZybwhysrs() {
        return zybwhysrs;
    }

    public void setZybwhysrs(String zybwhysrs) {
        this.zybwhysrs = zybwhysrs;
    }

    @Basic
    @Column(name = "ZYBWHZYCSS", nullable = true, length = 20)
    public String getZybwhzycss() {
        return zybwhzycss;
    }

    public void setZybwhzycss(String zybwhzycss) {
        this.zybwhzycss = zybwhzycss;
    }

    @Basic
    @Column(name = "ZYBWHYSLB", nullable = true, length = 50)
    public String getZybwhyslb() {
        return zybwhyslb;
    }

    public void setZybwhyslb(String zybwhyslb) {
        this.zybwhyslb = zybwhyslb;
    }

    @Basic
    @Column(name = "ZYBWHYS", nullable = true, length = 2500)
    public String getZybwhys() {
        return zybwhys;
    }

    public void setZybwhys(String zybwhys) {
        this.zybwhys = zybwhys;
    }

    @Basic
    @Column(name = "GYLC", nullable = true, length = 500)
    public String getGylc() {
        return gylc;
    }

    public void setGylc(String gylc) {
        this.gylc = gylc;
    }

    @Basic
    @Column(name = "ZYYFL", nullable = true, length = 200)
    public String getZyyfl() {
        return zyyfl;
    }

    public void setZyyfl(String zyyfl) {
        this.zyyfl = zyyfl;
    }

    @Basic
    @Column(name = "TJTTLX", nullable = true, precision = 0)
    public Boolean getTjttlx() {
        return tjttlx;
    }

    public void setTjttlx(Boolean tjttlx) {
        this.tjttlx = tjttlx;
    }

    @Basic
    @Column(name = "ZYCP", nullable = true, length = 200)
    public String getZycp() {
        return zycp;
    }

    public void setZycp(String zycp) {
        this.zycp = zycp;
    }

    @Basic
    @Column(name = "KHSCTJDWDZ", nullable = true, length = 500)
    public String getKhsctjdwdz() {
        return khsctjdwdz;
    }

    public void setKhsctjdwdz(String khsctjdwdz) {
        this.khsctjdwdz = khsctjdwdz;
    }

    @Basic
    @Column(name = "BZ", nullable = true, length = 2500)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Basic
    @Column(name = "SJRQ", nullable = true)
    public Date getSjrq() {
        return sjrq;
    }

    public void setSjrq(Date sjrq) {
        this.sjrq = sjrq;
    }

    @Basic
    @Column(name = "KHZT", nullable = true, length = 5)
    public String getKhzt() {
        return khzt;
    }

    public void setKhzt(String khzt) {
        this.khzt = khzt;
    }

    @Basic
    @Column(name = "IS_DELETE", nullable = true, precision = 0)
    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    @Basic
    @Column(name = "CLIENTID", nullable = true, length = 32)
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    @Basic
    @Column(name = "LDFPID", nullable = true, length = 32)
    public String getLdfpid() {
        return ldfpid;
    }

    public void setLdfpid(String ldfpid) {
        this.ldfpid = ldfpid;
    }

    @Basic
    @Column(name = "XSJLID", nullable = true, length = 32)
    public String getXsjlid() {
        return xsjlid;
    }

    public void setXsjlid(String xsjlid) {
        this.xsjlid = xsjlid;
    }

    @Basic
    @Column(name = "FZXID", nullable = true, length = 50)
    public String getFzxid() {
        return fzxid;
    }

    public void setFzxid(String fzxid) {
        this.fzxid = fzxid;
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
    @Column(name = "LOST", nullable = true, precision = 0)
    public Boolean getLost() {
        return lost;
    }

    public void setLost(Boolean lost) {
        this.lost = lost;
    }

    @Basic
    @Column(name = "INT_ID", nullable = true, precision = 0)
    public Long getIntId() {
        return intId;
    }

    public void setIntId(Long intId) {
        this.intId = intId;
    }

    @Basic
    @Column(name = "BRIEF_TEXT", nullable = true, length = 3000)
    public String getBriefText() {
        return briefText;
    }

    public void setBriefText(String briefText) {
        this.briefText = briefText;
    }

    @Basic
    @Column(name = "SOCIAL_CREDIT_CODE", nullable = true, length = 25)
    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    @Basic
    @Column(name = "PROVINCE", nullable = true, length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "CITY", nullable = true, length = 20)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "DISTRICT", nullable = true, length = 20)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Basic
    @Column(name = "STREET", nullable = true, length = 20)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "INDUS_TYPE_CODE", nullable = true, length = 10)
    public String getIndusTypeCode() {
        return indusTypeCode;
    }

    public void setIndusTypeCode(String indusTypeCode) {
        this.indusTypeCode = indusTypeCode;
    }

    @Basic
    @Column(name = "ECONOMY_CODE", nullable = true, length = 10)
    public String getEconomyCode() {
        return economyCode;
    }

    public void setEconomyCode(String economyCode) {
        this.economyCode = economyCode;
    }

    @Basic
    @Column(name = "CRPT_SIZE_CODE", nullable = true, length = 10)
    public String getCrptSizeCode() {
        return crptSizeCode;
    }

    public void setCrptSizeCode(String crptSizeCode) {
        this.crptSizeCode = crptSizeCode;
    }

    @Basic
    @Column(name = "WORK_FORCE", nullable = true, precision = 0)
    public Long getWorkForce() {
        return workForce;
    }

    public void setWorkForce(Long workForce) {
        this.workForce = workForce;
    }

    @Basic
    @Column(name = "HOLD_CARD_MAN", nullable = true, precision = 0)
    public Long getHoldCardMan() {
        return holdCardMan;
    }

    public void setHoldCardMan(Long holdCardMan) {
        this.holdCardMan = holdCardMan;
    }

    @Basic
    @Column(name = "WORKMAN_NUM", nullable = true, precision = 0)
    public Long getWorkmanNum() {
        return workmanNum;
    }

    public void setWorkmanNum(Long workmanNum) {
        this.workmanNum = workmanNum;
    }

    @Basic
    @Column(name = "WORKMISTRESS_NUM", nullable = true, precision = 0)
    public Long getWorkmistressNum() {
        return workmistressNum;
    }

    public void setWorkmistressNum(Long workmistressNum) {
        this.workmistressNum = workmistressNum;
    }

//    @Basic
//    @Column(name = "POSTALCODE", nullable = true, length = 6)
    @Transient
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Basic
    @Column(name = "WORK_AREA", nullable = true, length = 25)
    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    @Basic
    @Column(name = "REGISTER_FUND", nullable = true, length = 25)
    public String getRegisterFund() {
        return registerFund;
    }

    public void setRegisterFund(String registerFund) {
        this.registerFund = registerFund;
    }

    @Basic
    @Column(name = "SAFETY_PRINCIPAL", nullable = true, length = 25)
    public String getSafetyPrincipal() {
        return safetyPrincipal;
    }

    public void setSafetyPrincipal(String safetyPrincipal) {
        this.safetyPrincipal = safetyPrincipal;
    }

    @Basic
    @Column(name = "BUILD_DATE", nullable = true)
    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    @Basic
    @Column(name = "LINKMAN1", nullable = true, length = 25)
    public String getLinkman1() {
        return linkman1;
    }

    public void setLinkman1(String linkman1) {
        this.linkman1 = linkman1;
    }

    @Basic
    @Column(name = "POSITION1", nullable = true, length = 25)
    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    @Basic
    @Column(name = "LINKPHONE1", nullable = true, length = 15)
    public String getLinkphone1() {
        return linkphone1;
    }

    public void setLinkphone1(String linkphone1) {
        this.linkphone1 = linkphone1;
    }

    @Basic
    @Column(name = "LINKMAN2", nullable = true, length = 25)
    public String getLinkman2() {
        return linkman2;
    }

    public void setLinkman2(String linkman2) {
        this.linkman2 = linkman2;
    }

    @Basic
    @Column(name = "POSITION2", nullable = true, length = 25)
    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    @Basic
    @Column(name = "LINKPHONE2", nullable = true, length = 15)
    public String getLinkphone2() {
        return linkphone2;
    }

    public void setLinkphone2(String linkphone2) {
        this.linkphone2 = linkphone2;
    }

    @Basic
    @Column(name = "SAFEPOSITION", nullable = true, length = 25)
    public String getSafeposition() {
        return safeposition;
    }

    public void setSafeposition(String safeposition) {
        this.safeposition = safeposition;
    }

    @Basic
    @Column(name = "SAFEPHONE", nullable = true, length = 15)
    public String getSafephone() {
        return safephone;
    }

    public void setSafephone(String safephone) {
        this.safephone = safephone;
    }

    @Basic
    @Column(name = "SUBJE_CONN", nullable = true, length = 50)
    public String getSubjeConn() {
        return subjeConn;
    }

    public void setSubjeConn(String subjeConn) {
        this.subjeConn = subjeConn;
    }

    @Basic
    @Column(name = "ENROL_ADDRESS", nullable = true, length = 100)
    public String getEnrolAddress() {
        return enrolAddress;
    }

    public void setEnrolAddress(String enrolAddress) {
        this.enrolAddress = enrolAddress;
    }

    @Basic
    @Column(name = "ENROL_POSTALCODE", nullable = true, length = 6)
    public String getEnrolPostalcode() {
        return enrolPostalcode;
    }

    public void setEnrolPostalcode(String enrolPostalcode) {
        this.enrolPostalcode = enrolPostalcode;
    }

    @Basic
    @Column(name = "OCC_MANA_OFFICE", nullable = true, length = 100)
    public String getOccManaOffice() {
        return occManaOffice;
    }

    public void setOccManaOffice(String occManaOffice) {
        this.occManaOffice = occManaOffice;
    }

    @Basic
    @Column(name = "JINAN_STATUS", nullable = true, precision = 0)
    public Boolean getJinanStatus() {
        return jinanStatus;
    }

    public void setJinanStatus(Boolean jinanStatus) {
        this.jinanStatus = jinanStatus;
    }

    @Basic
    @Column(name = "JINAN_MSG", nullable = true, length = 300)
    public String getJinanMsg() {
        return jinanMsg;
    }

    public void setJinanMsg(String jinanMsg) {
        this.jinanMsg = jinanMsg;
    }

    @Basic
    @Column(name = "INDUS_TYPE_CODE1", nullable = true, length = 10)
    public String getIndusTypeCode1() {
        return indusTypeCode1;
    }

    public void setIndusTypeCode1(String indusTypeCode1) {
        this.indusTypeCode1 = indusTypeCode1;
    }

    @Basic
    @Column(name = "INDUS_TYPE_CODE2", nullable = true, length = 10)
    public String getIndusTypeCode2() {
        return indusTypeCode2;
    }

    public void setIndusTypeCode2(String indusTypeCode2) {
        this.indusTypeCode2 = indusTypeCode2;
    }

    @Basic
    @Column(name = "INDUS_TYPE_CODE3", nullable = true, length = 10)
    public String getIndusTypeCode3() {
        return indusTypeCode3;
    }

    public void setIndusTypeCode3(String indusTypeCode3) {
        this.indusTypeCode3 = indusTypeCode3;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 15)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sellcustomer that = (Sellcustomer) o;
        return Objects.equals(id, that.id) && Objects.equals(khdwmc, that.khdwmc) && Objects.equals(khdwsrm, that.khdwsrm) && Objects.equals(khdwlxr, that.khdwlxr) && Objects.equals(khdh, that.khdh) && Objects.equals(frdwmc, that.frdwmc) && Objects.equals(fddbr, that.fddbr) && Objects.equals(yzbm, that.yzbm) && Objects.equals(qygm, that.qygm) && Objects.equals(qyjjlx, that.qyjjlx) && Objects.equals(zywsfzr, that.zywsfzr) && Objects.equals(khdwzcdz, that.khdwzcdz) && Objects.equals(zywsgljg, that.zywsgljg) && Objects.equals(dwjgdm, that.dwjgdm) && Objects.equals(xsjl, that.xsjl) && Objects.equals(zclx, that.zclx) && Objects.equals(sshy, that.sshy) && Objects.equals(lsgx, that.lsgx) && Objects.equals(sjzgdw, that.sjzgdw) && Objects.equals(sjcyrs, that.sjcyrs) && Objects.equals(ldrs, that.ldrs) && Objects.equals(scgrs, that.scgrs) && Objects.equals(zybwhysrs, that.zybwhysrs) && Objects.equals(zybwhzycss, that.zybwhzycss) && Objects.equals(zybwhyslb, that.zybwhyslb) && Objects.equals(zybwhys, that.zybwhys) && Objects.equals(gylc, that.gylc) && Objects.equals(zyyfl, that.zyyfl) && Objects.equals(tjttlx, that.tjttlx) && Objects.equals(zycp, that.zycp) && Objects.equals(khsctjdwdz, that.khsctjdwdz) && Objects.equals(bz, that.bz) && Objects.equals(sjrq, that.sjrq) && Objects.equals(khzt, that.khzt) && Objects.equals(isDelete, that.isDelete) && Objects.equals(clientid, that.clientid) && Objects.equals(ldfpid, that.ldfpid) && Objects.equals(xsjlid, that.xsjlid) && Objects.equals(fzxid, that.fzxid) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(lost, that.lost) && Objects.equals(intId, that.intId) && Objects.equals(briefText, that.briefText) && Objects.equals(socialCreditCode, that.socialCreditCode) && Objects.equals(province, that.province) && Objects.equals(city, that.city) && Objects.equals(district, that.district) && Objects.equals(street, that.street) && Objects.equals(indusTypeCode, that.indusTypeCode) && Objects.equals(economyCode, that.economyCode) && Objects.equals(crptSizeCode, that.crptSizeCode) && Objects.equals(workForce, that.workForce) && Objects.equals(holdCardMan, that.holdCardMan) && Objects.equals(workmanNum, that.workmanNum) && Objects.equals(workmistressNum, that.workmistressNum) && Objects.equals(postalcode, that.postalcode) && Objects.equals(workArea, that.workArea) && Objects.equals(registerFund, that.registerFund) && Objects.equals(safetyPrincipal, that.safetyPrincipal) && Objects.equals(buildDate, that.buildDate) && Objects.equals(linkman1, that.linkman1) && Objects.equals(position1, that.position1) && Objects.equals(linkphone1, that.linkphone1) && Objects.equals(linkman2, that.linkman2) && Objects.equals(position2, that.position2) && Objects.equals(linkphone2, that.linkphone2) && Objects.equals(safeposition, that.safeposition) && Objects.equals(safephone, that.safephone) && Objects.equals(subjeConn, that.subjeConn) && Objects.equals(enrolAddress, that.enrolAddress) && Objects.equals(enrolPostalcode, that.enrolPostalcode) && Objects.equals(occManaOffice, that.occManaOffice) && Objects.equals(jinanStatus, that.jinanStatus) && Objects.equals(jinanMsg, that.jinanMsg) && Objects.equals(indusTypeCode1, that.indusTypeCode1) && Objects.equals(indusTypeCode2, that.indusTypeCode2) && Objects.equals(indusTypeCode3, that.indusTypeCode3) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, khdwmc, khdwsrm, khdwlxr, khdh, frdwmc, fddbr, yzbm, qygm, qyjjlx, zywsfzr, khdwzcdz, zywsgljg, dwjgdm, xsjl, zclx, sshy, lsgx, sjzgdw, sjcyrs, ldrs, scgrs, zybwhysrs, zybwhzycss, zybwhyslb, zybwhys, gylc, zyyfl, tjttlx, zycp, khsctjdwdz, bz, sjrq, khzt, isDelete, clientid, ldfpid, xsjlid, fzxid, createdate, modifydate, lost, intId, briefText, socialCreditCode, province, city, district, street, indusTypeCode, economyCode, crptSizeCode, workForce, holdCardMan, workmanNum, workmistressNum, postalcode, workArea, registerFund, safetyPrincipal, buildDate, linkman1, position1, linkphone1, linkman2, position2, linkphone2, safeposition, safephone, subjeConn, enrolAddress, enrolPostalcode, occManaOffice, jinanStatus, jinanMsg, indusTypeCode1, indusTypeCode2, indusTypeCode3, phone);
    }

    @Basic
    @Column(name = "RAU_KHDWMC", nullable = true, length = 255)
    public String getRauKhdwmc() {
        return rauKhdwmc;
    }

    public void setRauKhdwmc(String rauKhdwmc) {
        this.rauKhdwmc = rauKhdwmc;
    }

    @Basic
    @Column(name = "RAU_SOCIAL_CREDIT_CODE", nullable = true, length = 255)
    public String getRauSocialCreditCode() {
        return rauSocialCreditCode;
    }

    public void setRauSocialCreditCode(String rauSocialCreditCode) {
        this.rauSocialCreditCode = rauSocialCreditCode;
    }

    @Basic
    @Column(name = "RAU_ECONOMY_CODE", nullable = true, length = 255)
    public String getRauEconomyCode() {
        return rauEconomyCode;
    }

    public void setRauEconomyCode(String rauEconomyCode) {
        this.rauEconomyCode = rauEconomyCode;
    }

    @Basic
    @Column(name = "RAU_INDUS_TYPE_CODE1", nullable = true, length = 255)
    public String getRauIndusTypeCode1() {
        return rauIndusTypeCode1;
    }

    public void setRauIndusTypeCode1(String rauIndusTypeCode1) {
        this.rauIndusTypeCode1 = rauIndusTypeCode1;
    }

    @Basic
    @Column(name = "RAU_INDUS_TYPE_CODE2", nullable = true, length = 255)
    public String getRauIndusTypeCode2() {
        return rauIndusTypeCode2;
    }

    public void setRauIndusTypeCode2(String rauIndusTypeCode2) {
        this.rauIndusTypeCode2 = rauIndusTypeCode2;
    }

    @Basic
    @Column(name = "RAU_INDUS_TYPE_CODE3", nullable = true, length = 255)
    public String getRauIndusTypeCode3() {
        return rauIndusTypeCode3;
    }

    public void setRauIndusTypeCode3(String rauIndusTypeCode3) {
        this.rauIndusTypeCode3 = rauIndusTypeCode3;
    }

    @Basic
    @Column(name = "RAU_INDUS_TYPE_CODE", nullable = true, length = 255)
    public String getRauIndusTypeCode() {
        return rauIndusTypeCode;
    }

    public void setRauIndusTypeCode(String rauIndusTypeCode) {
        this.rauIndusTypeCode = rauIndusTypeCode;
    }

    @Basic
    @Column(name = "RAU_QYGM", nullable = true, length = 255)
    public String getRauQygm() {
        return rauQygm;
    }

    public void setRauQygm(String rauQygm) {
        this.rauQygm = rauQygm;
    }

    @Basic
    @Column(name = "RAU_PROVINCE", nullable = true, length = 255)
    public String getRauProvince() {
        return rauProvince;
    }

    public void setRauProvince(String rauProvince) {
        this.rauProvince = rauProvince;
    }

    @Basic
    @Column(name = "RAU_CITY", nullable = true, length = 255)
    public String getRauCity() {
        return rauCity;
    }

    public void setRauCity(String rauCity) {
        this.rauCity = rauCity;
    }

    @Basic
    @Column(name = "RAU_DISTRICT", nullable = true, length = 255)
    public String getRauDistrict() {
        return rauDistrict;
    }

    public void setRauDistrict(String rauDistrict) {
        this.rauDistrict = rauDistrict;
    }

    @Basic
    @Column(name = "RAU_STREET", nullable = true, length = 255)
    public String getRauStreet() {
        return rauStreet;
    }

    public void setRauStreet(String rauStreet) {
        this.rauStreet = rauStreet;
    }

    @Basic
    @Column(name = "UNITAREA", nullable = true, length = 255)
    public String getUnitarea() {
        return unitarea;
    }

    public void setUnitarea(String unitarea) {
        this.unitarea = unitarea;
    }

    @Basic
    @Column(name = "RAU_UNITAREA", nullable = true, length = 255)
    public String getRauUnitarea() {
        return rauUnitarea;
    }

    public void setRauUnitarea(String rauUnitarea) {
        this.rauUnitarea = rauUnitarea;
    }

    @Basic
    @Column(name = "LICENSE_NAME")
    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }
}