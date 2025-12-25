package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MD_REPORT")
public class Report {
    private String id;
    private String patientcode;
    private Time createdate;
    private Time modifydate;
    private Boolean diseaseHealth;
    private Boolean isPrintMessage;
    private String urlWord;
    private String urlPdf;
    private Long status;
    private Boolean isTotal;
    private String printMan;
    private String printId;
    private Time printTime;
    private Byte createNum;
    private Byte printNum;
    private String firstId;
    private String firstMan;
    private Time firstTime;
    private String firstReason;
    private String secondId;
    private String secondMan;
    private Time secondTime;
    private String secondReason;
    private String lastId;
    private String lastMan;
    private Time lastTime;
    private String lastReason;
    private String joinPersonId;
    private String joinPersonMan;
    private Time joinTime;
    private String revPersonId;
    private String revPersonMan;
    private Time revTime;
    private String grantId;
    private String chestNum;
    private String notificationResult;
    private String getterId;
    private String getterPhone;
    private String getterName;
    private String issueId;
    private String returnId;
    private Time getTime;
    private Time returnTime;
    private String expressId;
    private String expressNum;
    private String patientname;
    private String sex;
    private Long age;
    private String idOrg;
    private String orgName;
    private Time dateregister;
    private String phone;
    private String idOpendoctor;
    private String doctorapply;
    private String idDoctorfinal;
    private String doctorfinalNameR;
    private Time datefinalexamed;
    private Long numorgresv;
    private Boolean tbbz;
    private Time notifyDate;
    private String notifyMemo;
    private String generateName;
    private String generateHint;
    private Time generateDate;
    private Long shortCode;
    private String configId;
    private Boolean isNuclein;

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PATIENTCODE", nullable = true, length = 13)
    public String getPatientcode() {
        return patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }

    @Basic
    @Column(name = "CREATEDATE", nullable = true)
    public Time getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Time createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "MODIFYDATE", nullable = true)
    public Time getModifydate() {
        return modifydate;
    }

    public void setModifydate(Time modifydate) {
        this.modifydate = modifydate;
    }

    @Basic
    @Column(name = "DISEASE_HEALTH", nullable = true, precision = 0)
    public Boolean getDiseaseHealth() {
        return diseaseHealth;
    }

    public void setDiseaseHealth(Boolean diseaseHealth) {
        this.diseaseHealth = diseaseHealth;
    }

    @Basic
    @Column(name = "IS_PRINT_MESSAGE", nullable = true, precision = 0)
    public Boolean getPrintMessage() {
        return isPrintMessage;
    }

    public void setPrintMessage(Boolean printMessage) {
        isPrintMessage = printMessage;
    }

    @Basic
    @Column(name = "URL_WORD", nullable = true, length = 500)
    public String getUrlWord() {
        return urlWord;
    }

    public void setUrlWord(String urlWord) {
        this.urlWord = urlWord;
    }

    @Basic
    @Column(name = "URL_PDF", nullable = true, length = 500)
    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, precision = 0)
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "IS_TOTAL", nullable = true, precision = 0)
    public Boolean getTotal() {
        return isTotal;
    }

    public void setTotal(Boolean total) {
        isTotal = total;
    }

    @Basic
    @Column(name = "PRINT_MAN", nullable = true, length = 50)
    public String getPrintMan() {
        return printMan;
    }

    public void setPrintMan(String printMan) {
        this.printMan = printMan;
    }

    @Basic
    @Column(name = "PRINT_ID", nullable = true, length = 32)
    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    @Basic
    @Column(name = "PRINT_TIME", nullable = true)
    public Time getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Time printTime) {
        this.printTime = printTime;
    }

    @Basic
    @Column(name = "CREATE_NUM", nullable = true, precision = 0)
    public Byte getCreateNum() {
        return createNum;
    }

    public void setCreateNum(Byte createNum) {
        this.createNum = createNum;
    }

    @Basic
    @Column(name = "PRINT_NUM", nullable = true, precision = 0)
    public Byte getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Byte printNum) {
        this.printNum = printNum;
    }

    @Basic
    @Column(name = "FIRST_ID", nullable = true, length = 32)
    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    @Basic
    @Column(name = "FIRST_MAN", nullable = true, length = 32)
    public String getFirstMan() {
        return firstMan;
    }

    public void setFirstMan(String firstMan) {
        this.firstMan = firstMan;
    }

    @Basic
    @Column(name = "FIRST_TIME", nullable = true)
    public Time getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Time firstTime) {
        this.firstTime = firstTime;
    }

    @Basic
    @Column(name = "FIRST_REASON", nullable = true, length = 500)
    public String getFirstReason() {
        return firstReason;
    }

    public void setFirstReason(String firstReason) {
        this.firstReason = firstReason;
    }

    @Basic
    @Column(name = "SECOND_ID", nullable = true, length = 32)
    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    @Basic
    @Column(name = "SECOND_MAN", nullable = true, length = 32)
    public String getSecondMan() {
        return secondMan;
    }

    public void setSecondMan(String secondMan) {
        this.secondMan = secondMan;
    }

    @Basic
    @Column(name = "SECOND_TIME", nullable = true)
    public Time getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(Time secondTime) {
        this.secondTime = secondTime;
    }

    @Basic
    @Column(name = "SECOND_REASON", nullable = true, length = 500)
    public String getSecondReason() {
        return secondReason;
    }

    public void setSecondReason(String secondReason) {
        this.secondReason = secondReason;
    }

    @Basic
    @Column(name = "LAST_ID", nullable = true, length = 32)
    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    @Basic
    @Column(name = "LAST_MAN", nullable = true, length = 32)
    public String getLastMan() {
        return lastMan;
    }

    public void setLastMan(String lastMan) {
        this.lastMan = lastMan;
    }

    @Basic
    @Column(name = "LAST_TIME", nullable = true)
    public Time getLastTime() {
        return lastTime;
    }

    public void setLastTime(Time lastTime) {
        this.lastTime = lastTime;
    }

    @Basic
    @Column(name = "LAST_REASON", nullable = true, length = 500)
    public String getLastReason() {
        return lastReason;
    }

    public void setLastReason(String lastReason) {
        this.lastReason = lastReason;
    }

    @Basic
    @Column(name = "JOIN_PERSON_ID", nullable = true, length = 32)
    public String getJoinPersonId() {
        return joinPersonId;
    }

    public void setJoinPersonId(String joinPersonId) {
        this.joinPersonId = joinPersonId;
    }

    @Basic
    @Column(name = "JOIN_PERSON_MAN", nullable = true, length = 32)
    public String getJoinPersonMan() {
        return joinPersonMan;
    }

    public void setJoinPersonMan(String joinPersonMan) {
        this.joinPersonMan = joinPersonMan;
    }

    @Basic
    @Column(name = "JOIN_TIME", nullable = true)
    public Time getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Time joinTime) {
        this.joinTime = joinTime;
    }

    @Basic
    @Column(name = "REV_PERSON_ID", nullable = true, length = 32)
    public String getRevPersonId() {
        return revPersonId;
    }

    public void setRevPersonId(String revPersonId) {
        this.revPersonId = revPersonId;
    }

    @Basic
    @Column(name = "REV_PERSON_MAN", nullable = true, length = 32)
    public String getRevPersonMan() {
        return revPersonMan;
    }

    public void setRevPersonMan(String revPersonMan) {
        this.revPersonMan = revPersonMan;
    }

    @Basic
    @Column(name = "REV_TIME", nullable = true)
    public Time getRevTime() {
        return revTime;
    }

    public void setRevTime(Time revTime) {
        this.revTime = revTime;
    }

    @Basic
    @Column(name = "GRANT_ID", nullable = true, length = 32)
    public String getGrantId() {
        return grantId;
    }

    public void setGrantId(String grantId) {
        this.grantId = grantId;
    }

    @Basic
    @Column(name = "CHEST_NUM", nullable = true, length = 32)
    public String getChestNum() {
        return chestNum;
    }

    public void setChestNum(String chestNum) {
        this.chestNum = chestNum;
    }

    @Basic
    @Column(name = "NOTIFICATION_RESULT", nullable = true, length = 10)
    public String getNotificationResult() {
        return notificationResult;
    }

    public void setNotificationResult(String notificationResult) {
        this.notificationResult = notificationResult;
    }

    @Basic
    @Column(name = "GETTER_ID", nullable = true, length = 32)
    public String getGetterId() {
        return getterId;
    }

    public void setGetterId(String getterId) {
        this.getterId = getterId;
    }

    @Basic
    @Column(name = "GETTER_PHONE", nullable = true, length = 50)
    public String getGetterPhone() {
        return getterPhone;
    }

    public void setGetterPhone(String getterPhone) {
        this.getterPhone = getterPhone;
    }

    @Basic
    @Column(name = "GETTER_NAME", nullable = true, length = 20)
    public String getGetterName() {
        return getterName;
    }

    public void setGetterName(String getterName) {
        this.getterName = getterName;
    }

    @Basic
    @Column(name = "ISSUE_ID", nullable = true, length = 32)
    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    @Basic
    @Column(name = "RETURN_ID", nullable = true, length = 32)
    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    @Basic
    @Column(name = "GET_TIME", nullable = true)
    public Time getGetTime() {
        return getTime;
    }

    public void setGetTime(Time getTime) {
        this.getTime = getTime;
    }

    @Basic
    @Column(name = "RETURN_TIME", nullable = true)
    public Time getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Time returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "EXPRESS_ID", nullable = true, length = 32)
    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    @Basic
    @Column(name = "EXPRESS_NUM", nullable = true, length = 50)
    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    @Basic
    @Column(name = "PATIENTNAME", nullable = true, length = 50)
    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    @Basic
    @Column(name = "SEX", nullable = true, length = 8)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "AGE", nullable = true, precision = 0)
    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Basic
    @Column(name = "ID_ORG", nullable = true, length = 32)
    public String getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(String idOrg) {
        this.idOrg = idOrg;
    }

    @Basic
    @Column(name = "ORG_NAME", nullable = true, length = 50)
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Basic
    @Column(name = "DATEREGISTER", nullable = true)
    public Time getDateregister() {
        return dateregister;
    }

    public void setDateregister(Time dateregister) {
        this.dateregister = dateregister;
    }

    @Basic
    @Column(name = "PHONE", nullable = true, length = 30)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "ID_OPENDOCTOR", nullable = true, length = 32)
    public String getIdOpendoctor() {
        return idOpendoctor;
    }

    public void setIdOpendoctor(String idOpendoctor) {
        this.idOpendoctor = idOpendoctor;
    }

    @Basic
    @Column(name = "DOCTORAPPLY", nullable = true, length = 16)
    public String getDoctorapply() {
        return doctorapply;
    }

    public void setDoctorapply(String doctorapply) {
        this.doctorapply = doctorapply;
    }

    @Basic
    @Column(name = "ID_DOCTORFINAL", nullable = true, length = 32)
    public String getIdDoctorfinal() {
        return idDoctorfinal;
    }

    public void setIdDoctorfinal(String idDoctorfinal) {
        this.idDoctorfinal = idDoctorfinal;
    }

    @Basic
    @Column(name = "DOCTORFINAL_NAME_R", nullable = true, length = 12)
    public String getDoctorfinalNameR() {
        return doctorfinalNameR;
    }

    public void setDoctorfinalNameR(String doctorfinalNameR) {
        this.doctorfinalNameR = doctorfinalNameR;
    }

    @Basic
    @Column(name = "DATEFINALEXAMED", nullable = true)
    public Time getDatefinalexamed() {
        return datefinalexamed;
    }

    public void setDatefinalexamed(Time datefinalexamed) {
        this.datefinalexamed = datefinalexamed;
    }

    @Basic
    @Column(name = "NUMORGRESV", nullable = true, precision = 0)
    public Long getNumorgresv() {
        return numorgresv;
    }

    public void setNumorgresv(Long numorgresv) {
        this.numorgresv = numorgresv;
    }

    @Basic
    @Column(name = "TBBZ", nullable = true, precision = 0)
    public Boolean getTbbz() {
        return tbbz;
    }

    public void setTbbz(Boolean tbbz) {
        this.tbbz = tbbz;
    }

    @Basic
    @Column(name = "NOTIFY_DATE", nullable = true)
    public Time getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Time notifyDate) {
        this.notifyDate = notifyDate;
    }

    @Basic
    @Column(name = "NOTIFY_MEMO", nullable = true, length = 2000)
    public String getNotifyMemo() {
        return notifyMemo;
    }

    public void setNotifyMemo(String notifyMemo) {
        this.notifyMemo = notifyMemo;
    }

    @Basic
    @Column(name = "GENERATE_NAME", nullable = true, length = 50)
    public String getGenerateName() {
        return generateName;
    }

    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }

    @Basic
    @Column(name = "GENERATE_HINT", nullable = true)
    public String getGenerateHint() {
        return generateHint;
    }

    public void setGenerateHint(String generateHint) {
        this.generateHint = generateHint;
    }

    @Basic
    @Column(name = "GENERATE_DATE", nullable = true)
    public Time getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Time generateDate) {
        this.generateDate = generateDate;
    }

    @Basic
    @Column(name = "SHORT_CODE", nullable = true, precision = 0)
    public Long getShortCode() {
        return shortCode;
    }

    public void setShortCode(Long shortCode) {
        this.shortCode = shortCode;
    }

    @Basic
    @Column(name = "CONFIG_ID", nullable = true, length = 32)
    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    @Basic
    @Column(name = "IS_NUCLEIN", nullable = true, precision = 0)
    public Boolean getNuclein() {
        return isNuclein;
    }

    public void setNuclein(Boolean nuclein) {
        isNuclein = nuclein;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Objects.equals(patientcode, report.patientcode) && Objects.equals(createdate, report.createdate) && Objects.equals(modifydate, report.modifydate) && Objects.equals(diseaseHealth, report.diseaseHealth) && Objects.equals(isPrintMessage, report.isPrintMessage) && Objects.equals(urlWord, report.urlWord) && Objects.equals(urlPdf, report.urlPdf) && Objects.equals(status, report.status) && Objects.equals(isTotal, report.isTotal) && Objects.equals(printMan, report.printMan) && Objects.equals(printId, report.printId) && Objects.equals(printTime, report.printTime) && Objects.equals(createNum, report.createNum) && Objects.equals(printNum, report.printNum) && Objects.equals(firstId, report.firstId) && Objects.equals(firstMan, report.firstMan) && Objects.equals(firstTime, report.firstTime) && Objects.equals(firstReason, report.firstReason) && Objects.equals(secondId, report.secondId) && Objects.equals(secondMan, report.secondMan) && Objects.equals(secondTime, report.secondTime) && Objects.equals(secondReason, report.secondReason) && Objects.equals(lastId, report.lastId) && Objects.equals(lastMan, report.lastMan) && Objects.equals(lastTime, report.lastTime) && Objects.equals(lastReason, report.lastReason) && Objects.equals(joinPersonId, report.joinPersonId) && Objects.equals(joinPersonMan, report.joinPersonMan) && Objects.equals(joinTime, report.joinTime) && Objects.equals(revPersonId, report.revPersonId) && Objects.equals(revPersonMan, report.revPersonMan) && Objects.equals(revTime, report.revTime) && Objects.equals(grantId, report.grantId) && Objects.equals(chestNum, report.chestNum) && Objects.equals(notificationResult, report.notificationResult) && Objects.equals(getterId, report.getterId) && Objects.equals(getterPhone, report.getterPhone) && Objects.equals(getterName, report.getterName) && Objects.equals(issueId, report.issueId) && Objects.equals(returnId, report.returnId) && Objects.equals(getTime, report.getTime) && Objects.equals(returnTime, report.returnTime) && Objects.equals(expressId, report.expressId) && Objects.equals(expressNum, report.expressNum) && Objects.equals(patientname, report.patientname) && Objects.equals(sex, report.sex) && Objects.equals(age, report.age) && Objects.equals(idOrg, report.idOrg) && Objects.equals(orgName, report.orgName) && Objects.equals(dateregister, report.dateregister) && Objects.equals(phone, report.phone) && Objects.equals(idOpendoctor, report.idOpendoctor) && Objects.equals(doctorapply, report.doctorapply) && Objects.equals(idDoctorfinal, report.idDoctorfinal) && Objects.equals(doctorfinalNameR, report.doctorfinalNameR) && Objects.equals(datefinalexamed, report.datefinalexamed) && Objects.equals(numorgresv, report.numorgresv) && Objects.equals(tbbz, report.tbbz) && Objects.equals(notifyDate, report.notifyDate) && Objects.equals(notifyMemo, report.notifyMemo) && Objects.equals(generateName, report.generateName) && Objects.equals(generateHint, report.generateHint) && Objects.equals(generateDate, report.generateDate) && Objects.equals(shortCode, report.shortCode) && Objects.equals(configId, report.configId) && Objects.equals(isNuclein, report.isNuclein);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientcode, createdate, modifydate, diseaseHealth, isPrintMessage, urlWord, urlPdf, status, isTotal, printMan, printId, printTime, createNum, printNum, firstId, firstMan, firstTime, firstReason, secondId, secondMan, secondTime, secondReason, lastId, lastMan, lastTime, lastReason, joinPersonId, joinPersonMan, joinTime, revPersonId, revPersonMan, revTime, grantId, chestNum, notificationResult, getterId, getterPhone, getterName, issueId, returnId, getTime, returnTime, expressId, expressNum, patientname, sex, age, idOrg, orgName, dateregister, phone, idOpendoctor, doctorapply, idDoctorfinal, doctorfinalNameR, datefinalexamed, numorgresv, tbbz, notifyDate, notifyMemo, generateName, generateHint, generateDate, shortCode, configId, isNuclein);
    }
}