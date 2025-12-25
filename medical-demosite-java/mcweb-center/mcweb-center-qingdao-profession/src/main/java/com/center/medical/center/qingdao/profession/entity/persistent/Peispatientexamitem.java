package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_PEISPATIENTEXAMITEM")
public class Peispatientexamitem {
    private String associativeTable;
    private String id;
    private Long idPatientexamitem;
    private Long idPatientexamdepart;
    private Long idPatientfeeitem;
    private String patientcode;
    private String idExamfeeitem;
    private String examfeeitem;
    private String idExamitem;
    private String examitemNameR;
    private String examitemCodeR;
    private Long idExamitemvalue;
    private Integer severedegree;
    private String refrange;
    private String examitemvalues;
    private String examitemvaluestext;
    private String examitemvaluesshort;
    private Double examitemvaluesnumber;
    private String labitemflag;
    private Integer fLabitemnormal;
    private Double examitemvaluesnumber2;
    private Double examitemvaluesnumber3;
    private String befidDisporderR;
    private Date rowcreatetime;
    private Integer fLabrcvdfromlis;
    private String valueoper;
    private String valueoper2;
    private Date createdate;
    private Date modifydate;
    private String status;
    private String lisCode;
    private String units;
    private String depId;
    private String reportRange;
    private String examitemvaluesreport;
    private String patientName;
    private String examDoctor;
    private Date examDateTime;
    private String imageFullPath;
    private String auditName;
    private String inspectName;
    private Date auditDate;
    private Long reflow;
    private Long refhigh;
    private Long lisybbh;
    private String tableValue;
    private String zyConclusions;
    private String conclusions;
    private String sectionResultTwoData;
    private String ms;
    private String inputResult;
    private Integer isUnchecked;
    private String basconclusionId;
    private Integer positive;
    private String examitemNameprn;
    private String examfeeitemNameprn;
    private Integer type;
    private Integer posistive;
    private String zyStatus;
    private String inspectCode;
    private Long shortCode;
    private Date receiveDate;
//    private String adiconCode;

    @Basic
    @Column(name = "ASSOCIATIVE_TABLE", nullable = true, length = 50)
    public String getAssociativeTable() {
        return associativeTable;
    }

    public void setAssociativeTable(String associativeTable) {
        this.associativeTable = associativeTable;
    }

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ID_PATIENTEXAMITEM", nullable = true, precision = 0)
    public Long getIdPatientexamitem() {
        return idPatientexamitem;
    }

    public void setIdPatientexamitem(Long idPatientexamitem) {
        this.idPatientexamitem = idPatientexamitem;
    }

    @Basic
    @Column(name = "ID_PATIENTEXAMDEPART", nullable = true, precision = 0)
    public Long getIdPatientexamdepart() {
        return idPatientexamdepart;
    }

    public void setIdPatientexamdepart(Long idPatientexamdepart) {
        this.idPatientexamdepart = idPatientexamdepart;
    }

    @Basic
    @Column(name = "ID_PATIENTFEEITEM", nullable = true, precision = 0)
    public Long getIdPatientfeeitem() {
        return idPatientfeeitem;
    }

    public void setIdPatientfeeitem(Long idPatientfeeitem) {
        this.idPatientfeeitem = idPatientfeeitem;
    }

    @Basic
    @Column(name = "PATIENTCODE", nullable = true, length = 50)
    public String getPatientcode() {
        return patientcode;
    }

    public void setPatientcode(String patientcode) {
        this.patientcode = patientcode;
    }

    @Basic
    @Column(name = "ID_EXAMFEEITEM", nullable = true, length = 32)
    public String getIdExamfeeitem() {
        return idExamfeeitem;
    }

    public void setIdExamfeeitem(String idExamfeeitem) {
        this.idExamfeeitem = idExamfeeitem;
    }

    @Basic
    @Column(name = "EXAMFEEITEM", nullable = true, length = 150)
    public String getExamfeeitem() {
        return examfeeitem;
    }

    public void setExamfeeitem(String examfeeitem) {
        this.examfeeitem = examfeeitem;
    }

    @Basic
    @Column(name = "ID_EXAMITEM", nullable = true, length = 32)
    public String getIdExamitem() {
        return idExamitem;
    }

    public void setIdExamitem(String idExamitem) {
        this.idExamitem = idExamitem;
    }

    @Basic
    @Column(name = "EXAMITEM_NAME_R", nullable = true, length = 50)
    public String getExamitemNameR() {
        return examitemNameR;
    }

    public void setExamitemNameR(String examitemNameR) {
        this.examitemNameR = examitemNameR;
    }

    @Basic
    @Column(name = "EXAMITEM_CODE_R", nullable = true, length = 50)
    public String getExamitemCodeR() {
        return examitemCodeR;
    }

    public void setExamitemCodeR(String examitemCodeR) {
        this.examitemCodeR = examitemCodeR;
    }

    @Basic
    @Column(name = "ID_EXAMITEMVALUE", nullable = true, precision = 0)
    public Long getIdExamitemvalue() {
        return idExamitemvalue;
    }

    public void setIdExamitemvalue(Long idExamitemvalue) {
        this.idExamitemvalue = idExamitemvalue;
    }

    @Basic
    @Column(name = "SEVEREDEGREE", nullable = true, precision = 0)
    public Integer getSeveredegree() {
        return severedegree;
    }

    public void setSeveredegree(Integer severedegree) {
        this.severedegree = severedegree;
    }

    @Basic
    @Column(name = "REFRANGE", nullable = true, length = 500)
    public String getRefrange() {
        return refrange;
    }

    public void setRefrange(String refrange) {
        this.refrange = refrange;
    }

    @Basic
    @Column(name = "EXAMITEMVALUES", nullable = true, length = 3000)
    public String getExamitemvalues() {
        return examitemvalues;
    }

    public void setExamitemvalues(String examitemvalues) {
        this.examitemvalues = examitemvalues;
    }

    @Basic
    @Column(name = "EXAMITEMVALUESTEXT", nullable = true, length = 3000)
    public String getExamitemvaluestext() {
        return examitemvaluestext;
    }

    public void setExamitemvaluestext(String examitemvaluestext) {
        this.examitemvaluestext = examitemvaluestext;
    }

    @Basic
    @Column(name = "EXAMITEMVALUESSHORT", nullable = true, length = 600)
    public String getExamitemvaluesshort() {
        return examitemvaluesshort;
    }

    public void setExamitemvaluesshort(String examitemvaluesshort) {
        this.examitemvaluesshort = examitemvaluesshort;
    }

    @Basic
    @Column(name = "EXAMITEMVALUESNUMBER", nullable = true, precision = 0)
    public Double getExamitemvaluesnumber() {
        return examitemvaluesnumber;
    }

    public void setExamitemvaluesnumber(Double examitemvaluesnumber) {
        this.examitemvaluesnumber = examitemvaluesnumber;
    }

    @Basic
    @Column(name = "LABITEMFLAG", nullable = true, length = 10)
    public String getLabitemflag() {
        return labitemflag;
    }

    public void setLabitemflag(String labitemflag) {
        this.labitemflag = labitemflag;
    }

    @Basic
    @Column(name = "F_LABITEMNORMAL", nullable = true, precision = 0)
    public Integer getfLabitemnormal() {
        return fLabitemnormal;
    }

    public void setfLabitemnormal(Integer fLabitemnormal) {
        this.fLabitemnormal = fLabitemnormal;
    }

    @Basic
    @Column(name = "EXAMITEMVALUESNUMBER2", nullable = true, precision = 0)
    public Double getExamitemvaluesnumber2() {
        return examitemvaluesnumber2;
    }

    public void setExamitemvaluesnumber2(Double examitemvaluesnumber2) {
        this.examitemvaluesnumber2 = examitemvaluesnumber2;
    }

    @Basic
    @Column(name = "EXAMITEMVALUESNUMBER3", nullable = true, precision = 0)
    public Double getExamitemvaluesnumber3() {
        return examitemvaluesnumber3;
    }

    public void setExamitemvaluesnumber3(Double examitemvaluesnumber3) {
        this.examitemvaluesnumber3 = examitemvaluesnumber3;
    }

    @Basic
    @Column(name = "BEFID_DISPORDER_R", nullable = true, length = 10)
    public String getBefidDisporderR() {
        return befidDisporderR;
    }

    public void setBefidDisporderR(String befidDisporderR) {
        this.befidDisporderR = befidDisporderR;
    }

    @Basic
    @Column(name = "ROWCREATETIME", nullable = true)
    public Date getRowcreatetime() {
        return rowcreatetime;
    }

    public void setRowcreatetime(Date rowcreatetime) {
        this.rowcreatetime = rowcreatetime;
    }

    @Basic
    @Column(name = "F_LABRCVDFROMLIS", nullable = true, precision = 0)
    public Integer getfLabrcvdfromlis() {
        return fLabrcvdfromlis;
    }

    public void setfLabrcvdfromlis(Integer fLabrcvdfromlis) {
        this.fLabrcvdfromlis = fLabrcvdfromlis;
    }

    @Basic
    @Column(name = "VALUEOPER", nullable = true, length = 10)
    public String getValueoper() {
        return valueoper;
    }

    public void setValueoper(String valueoper) {
        this.valueoper = valueoper;
    }

    @Basic
    @Column(name = "VALUEOPER2", nullable = true, length = 10)
    public String getValueoper2() {
        return valueoper2;
    }

    public void setValueoper2(String valueoper2) {
        this.valueoper2 = valueoper2;
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
    @Column(name = "STATUS", nullable = true, length = 4)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "LIS_CODE", nullable = true, length = 20)
    public String getLisCode() {
        return lisCode;
    }

    public void setLisCode(String lisCode) {
        this.lisCode = lisCode;
    }

    @Basic
    @Column(name = "UNITS", nullable = true, length = 32)
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Basic
    @Column(name = "DEP_ID", nullable = true, length = 32)
    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    @Basic
    @Column(name = "REPORT_RANGE", nullable = true, length = 60)
    public String getReportRange() {
        return reportRange;
    }

    public void setReportRange(String reportRange) {
        this.reportRange = reportRange;
    }

    @Basic
    @Column(name = "EXAMITEMVALUESREPORT", nullable = true, length = 200)
    public String getExamitemvaluesreport() {
        return examitemvaluesreport;
    }

    public void setExamitemvaluesreport(String examitemvaluesreport) {
        this.examitemvaluesreport = examitemvaluesreport;
    }

    @Basic
    @Column(name = "PATIENT_NAME", nullable = true, length = 60)
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Basic
    @Column(name = "EXAM_DOCTOR", nullable = true, length = 60)
    public String getExamDoctor() {
        return examDoctor;
    }

    public void setExamDoctor(String examDoctor) {
        this.examDoctor = examDoctor;
    }

    @Basic
    @Column(name = "EXAM_DATE_TIME", nullable = true)
    public Date getExamDateTime() {
        return examDateTime;
    }

    public void setExamDateTime(Date examDateTime) {
        this.examDateTime = examDateTime;
    }

    @Basic
    @Column(name = "IMAGE_FULL_PATH", nullable = true, length = 500)
    public String getImageFullPath() {
        return imageFullPath;
    }

    public void setImageFullPath(String imageFullPath) {
        this.imageFullPath = imageFullPath;
    }

    @Basic
    @Column(name = "AUDIT_NAME", nullable = true, length = 60)
    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    @Basic
    @Column(name = "INSPECT_NAME", nullable = true, length = 60)
    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    @Basic
    @Column(name = "AUDIT_DATE", nullable = true)
    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    @Basic
    @Column(name = "REFLOW", nullable = true, precision = 0)
    public Long getReflow() {
        return reflow;
    }

    public void setReflow(Long reflow) {
        this.reflow = reflow;
    }

    @Basic
    @Column(name = "REFHIGH", nullable = true, precision = 0)
    public Long getRefhigh() {
        return refhigh;
    }

    public void setRefhigh(Long refhigh) {
        this.refhigh = refhigh;
    }

    @Basic
    @Column(name = "LISYBBH", nullable = true, precision = 0)
    public Long getLisybbh() {
        return lisybbh;
    }

    public void setLisybbh(Long lisybbh) {
        this.lisybbh = lisybbh;
    }

    @Basic
    @Column(name = "TABLE_VALUE", nullable = true, length = 2000)
    public String getTableValue() {
        return tableValue;
    }

    public void setTableValue(String tableValue) {
        this.tableValue = tableValue;
    }

    @Basic
    @Column(name = "ZY_CONCLUSIONS", nullable = true, length = 2000)
    public String getZyConclusions() {
        return zyConclusions;
    }

    public void setZyConclusions(String zyConclusions) {
        this.zyConclusions = zyConclusions;
    }

    @Basic
    @Column(name = "CONCLUSIONS", nullable = true, length = 2000)
    public String getConclusions() {
        return conclusions;
    }

    public void setConclusions(String conclusions) {
        this.conclusions = conclusions;
    }

    @Basic
    @Column(name = "SECTION_RESULT_TWO_DATA", nullable = true, length = 4000)
    public String getSectionResultTwoData() {
        return sectionResultTwoData;
    }

    public void setSectionResultTwoData(String sectionResultTwoData) {
        this.sectionResultTwoData = sectionResultTwoData;
    }

    @Basic
    @Column(name = "MS", nullable = true)
    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    @Basic
    @Column(name = "INPUT_RESULT", nullable = true, length = 200)
    public String getInputResult() {
        return inputResult;
    }

    public void setInputResult(String inputResult) {
        this.inputResult = inputResult;
    }

    @Basic
    @Column(name = "IS_UNCHECKED", nullable = true, precision = 0)
    public Integer getUnchecked() {
        return isUnchecked;
    }

    public void setUnchecked(Integer unchecked) {
        isUnchecked = unchecked;
    }

    @Basic
    @Column(name = "BASCONCLUSION_ID", nullable = true, length = 32)
    public String getBasconclusionId() {
        return basconclusionId;
    }

    public void setBasconclusionId(String basconclusionId) {
        this.basconclusionId = basconclusionId;
    }

    @Basic
    @Column(name = "POSITIVE", nullable = true, precision = 0)
    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    @Basic
    @Column(name = "EXAMITEM_NAMEPRN", nullable = true, length = 50)
    public String getExamitemNameprn() {
        return examitemNameprn;
    }

    public void setExamitemNameprn(String examitemNameprn) {
        this.examitemNameprn = examitemNameprn;
    }

    @Basic
    @Column(name = "EXAMFEEITEM_NAMEPRN", nullable = true, length = 50)
    public String getExamfeeitemNameprn() {
        return examfeeitemNameprn;
    }

    public void setExamfeeitemNameprn(String examfeeitemNameprn) {
        this.examfeeitemNameprn = examfeeitemNameprn;
    }

    @Basic
    @Column(name = "TYPE", nullable = true, precision = 0)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "POSISTIVE", nullable = true, precision = 0)
    public Integer getPosistive() {
        return posistive;
    }

    public void setPosistive(Integer posistive) {
        this.posistive = posistive;
    }

    @Basic
    @Column(name = "ZY_STATUS", nullable = true, length = 10)
    public String getZyStatus() {
        return zyStatus;
    }

    public void setZyStatus(String zyStatus) {
        this.zyStatus = zyStatus;
    }

    @Basic
    @Column(name = "INSPECT_CODE", nullable = true, length = 32)
    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
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
    @Column(name = "RECEIVE_DATE", nullable = true)
    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

//    @Basic
//    @Column(name = "ADICON_CODE", nullable = true, length = 60)
//    public String getAdiconCode() {
//        return adiconCode;
//    }
//
//    public void setAdiconCode(String adiconCode) {
//        this.adiconCode = adiconCode;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peispatientexamitem that = (Peispatientexamitem) o;
        return Objects.equals(associativeTable, that.associativeTable) && Objects.equals(id, that.id) && Objects.equals(idPatientexamitem, that.idPatientexamitem) && Objects.equals(idPatientexamdepart, that.idPatientexamdepart) && Objects.equals(idPatientfeeitem, that.idPatientfeeitem) && Objects.equals(patientcode, that.patientcode) && Objects.equals(idExamfeeitem, that.idExamfeeitem) && Objects.equals(examfeeitem, that.examfeeitem) && Objects.equals(idExamitem, that.idExamitem) && Objects.equals(examitemNameR, that.examitemNameR) && Objects.equals(examitemCodeR, that.examitemCodeR) && Objects.equals(idExamitemvalue, that.idExamitemvalue) && Objects.equals(severedegree, that.severedegree) && Objects.equals(refrange, that.refrange) && Objects.equals(examitemvalues, that.examitemvalues) && Objects.equals(examitemvaluestext, that.examitemvaluestext) && Objects.equals(examitemvaluesshort, that.examitemvaluesshort) && Objects.equals(examitemvaluesnumber, that.examitemvaluesnumber) && Objects.equals(labitemflag, that.labitemflag) && Objects.equals(fLabitemnormal, that.fLabitemnormal) && Objects.equals(examitemvaluesnumber2, that.examitemvaluesnumber2) && Objects.equals(examitemvaluesnumber3, that.examitemvaluesnumber3) && Objects.equals(befidDisporderR, that.befidDisporderR) && Objects.equals(rowcreatetime, that.rowcreatetime) && Objects.equals(fLabrcvdfromlis, that.fLabrcvdfromlis) && Objects.equals(valueoper, that.valueoper) && Objects.equals(valueoper2, that.valueoper2) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(status, that.status) && Objects.equals(lisCode, that.lisCode) && Objects.equals(units, that.units) && Objects.equals(depId, that.depId) && Objects.equals(reportRange, that.reportRange) && Objects.equals(examitemvaluesreport, that.examitemvaluesreport) && Objects.equals(patientName, that.patientName) && Objects.equals(examDoctor, that.examDoctor) && Objects.equals(examDateTime, that.examDateTime) && Objects.equals(imageFullPath, that.imageFullPath) && Objects.equals(auditName, that.auditName) && Objects.equals(inspectName, that.inspectName) && Objects.equals(auditDate, that.auditDate) && Objects.equals(reflow, that.reflow) && Objects.equals(refhigh, that.refhigh) && Objects.equals(lisybbh, that.lisybbh) && Objects.equals(tableValue, that.tableValue) && Objects.equals(zyConclusions, that.zyConclusions) && Objects.equals(conclusions, that.conclusions) && Objects.equals(sectionResultTwoData, that.sectionResultTwoData) && Objects.equals(ms, that.ms) && Objects.equals(inputResult, that.inputResult) && Objects.equals(isUnchecked, that.isUnchecked) && Objects.equals(basconclusionId, that.basconclusionId) && Objects.equals(positive, that.positive) && Objects.equals(examitemNameprn, that.examitemNameprn) && Objects.equals(examfeeitemNameprn, that.examfeeitemNameprn) && Objects.equals(type, that.type) && Objects.equals(posistive, that.posistive) && Objects.equals(zyStatus, that.zyStatus) && Objects.equals(inspectCode, that.inspectCode) && Objects.equals(shortCode, that.shortCode) && Objects.equals(receiveDate, that.receiveDate)
//                && Objects.equals(adiconCode, that.adiconCode)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(associativeTable, id, idPatientexamitem, idPatientexamdepart, idPatientfeeitem, patientcode, idExamfeeitem, examfeeitem, idExamitem, examitemNameR, examitemCodeR, idExamitemvalue, severedegree, refrange, examitemvalues, examitemvaluestext, examitemvaluesshort, examitemvaluesnumber, labitemflag, fLabitemnormal, examitemvaluesnumber2, examitemvaluesnumber3, befidDisporderR, rowcreatetime, fLabrcvdfromlis, valueoper, valueoper2, createdate, modifydate, status, lisCode, units, depId, reportRange, examitemvaluesreport, patientName, examDoctor, examDateTime, imageFullPath, auditName, inspectName, auditDate, reflow, refhigh, lisybbh, tableValue, zyConclusions, conclusions, sectionResultTwoData, ms, inputResult, isUnchecked, basconclusionId, positive, examitemNameprn, examfeeitemNameprn, type, posistive, zyStatus, inspectCode, shortCode, receiveDate
//                , adiconCode
        );
    }
}