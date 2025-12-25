package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MD_LUNG")
public class Lung {
    private String depId;
    private Time tjrq;
    private String xj;
    private String createId;
    private String modifyId;
    private String id;
    private Double fvc;
    private Double fev;
    private Double fevFvc;
    private Double percentageMmef;
    private Double predictMmef;
    private Double mmef;
    private Double percentageFeffa;
    private Double predictFeffa;
    private Double feffa;
    private Double percentageFeffb;
    private Double predictFeffb;
    private Double feffb;
    private Double percentageFeffc;
    private Double predictFeffc;
    private Double feffc;
    private String patientcode;
    private Time createdate;
    private Time modifydate;
    private Double predictFvc;
    private Double percentageFvc;
    private Double predictFev;
    private Double percentageFev;
    private Double predictFevFvc;
    private Double percentageFevFvc;

    @Basic
    @Column(name = "DEP_ID", nullable = true, length = 32)
    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    @Basic
    @Column(name = "TJRQ", nullable = true)
    public Time getTjrq() {
        return tjrq;
    }

    public void setTjrq(Time tjrq) {
        this.tjrq = tjrq;
    }

    @Basic
    @Column(name = "XJ", nullable = true, length = 2000)
    public String getXj() {
        return xj;
    }

    public void setXj(String xj) {
        this.xj = xj;
    }

    @Basic
    @Column(name = "CREATE_ID", nullable = true, length = 32)
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Basic
    @Column(name = "MODIFY_ID", nullable = true, length = 32)
    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
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
    @Column(name = "FVC", nullable = true, precision = 2)
    public Double getFvc() {
        return fvc;
    }

    public void setFvc(Double fvc) {
        this.fvc = fvc;
    }

    @Basic
    @Column(name = "FEV", nullable = true, precision = 2)
    public Double getFev() {
        return fev;
    }

    public void setFev(Double fev) {
        this.fev = fev;
    }

    @Basic
    @Column(name = "FEV_FVC", nullable = true, precision = 2)
    public Double getFevFvc() {
        return fevFvc;
    }

    public void setFevFvc(Double fevFvc) {
        this.fevFvc = fevFvc;
    }

    @Basic
    @Column(name = "PERCENTAGE_MMEF", nullable = true, precision = 2)
    public Double getPercentageMmef() {
        return percentageMmef;
    }

    public void setPercentageMmef(Double percentageMmef) {
        this.percentageMmef = percentageMmef;
    }

    @Basic
    @Column(name = "PREDICT_MMEF", nullable = true, precision = 2)
    public Double getPredictMmef() {
        return predictMmef;
    }

    public void setPredictMmef(Double predictMmef) {
        this.predictMmef = predictMmef;
    }

    @Basic
    @Column(name = "MMEF", nullable = true, precision = 2)
    public Double getMmef() {
        return mmef;
    }

    public void setMmef(Double mmef) {
        this.mmef = mmef;
    }

    @Basic
    @Column(name = "PERCENTAGE_FEFFA", nullable = true, precision = 2)
    public Double getPercentageFeffa() {
        return percentageFeffa;
    }

    public void setPercentageFeffa(Double percentageFeffa) {
        this.percentageFeffa = percentageFeffa;
    }

    @Basic
    @Column(name = "PREDICT_FEFFA", nullable = true, precision = 2)
    public Double getPredictFeffa() {
        return predictFeffa;
    }

    public void setPredictFeffa(Double predictFeffa) {
        this.predictFeffa = predictFeffa;
    }

    @Basic
    @Column(name = "FEFFA", nullable = true, precision = 2)
    public Double getFeffa() {
        return feffa;
    }

    public void setFeffa(Double feffa) {
        this.feffa = feffa;
    }

    @Basic
    @Column(name = "PERCENTAGE_FEFFB", nullable = true, precision = 2)
    public Double getPercentageFeffb() {
        return percentageFeffb;
    }

    public void setPercentageFeffb(Double percentageFeffb) {
        this.percentageFeffb = percentageFeffb;
    }

    @Basic
    @Column(name = "PREDICT_FEFFB", nullable = true, precision = 2)
    public Double getPredictFeffb() {
        return predictFeffb;
    }

    public void setPredictFeffb(Double predictFeffb) {
        this.predictFeffb = predictFeffb;
    }

    @Basic
    @Column(name = "FEFFB", nullable = true, precision = 2)
    public Double getFeffb() {
        return feffb;
    }

    public void setFeffb(Double feffb) {
        this.feffb = feffb;
    }

    @Basic
    @Column(name = "PERCENTAGE_FEFFC", nullable = true, precision = 2)
    public Double getPercentageFeffc() {
        return percentageFeffc;
    }

    public void setPercentageFeffc(Double percentageFeffc) {
        this.percentageFeffc = percentageFeffc;
    }

    @Basic
    @Column(name = "PREDICT_FEFFC", nullable = true, precision = 2)
    public Double getPredictFeffc() {
        return predictFeffc;
    }

    public void setPredictFeffc(Double predictFeffc) {
        this.predictFeffc = predictFeffc;
    }

    @Basic
    @Column(name = "FEFFC", nullable = true, precision = 2)
    public Double getFeffc() {
        return feffc;
    }

    public void setFeffc(Double feffc) {
        this.feffc = feffc;
    }

    @Basic
    @Column(name = "PATIENTCODE", nullable = true, length = 32)
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
    @Column(name = "PREDICT_FVC", nullable = true, precision = 2)
    public Double getPredictFvc() {
        return predictFvc;
    }

    public void setPredictFvc(Double predictFvc) {
        this.predictFvc = predictFvc;
    }

    @Basic
    @Column(name = "PERCENTAGE_FVC", nullable = true, precision = 2)
    public Double getPercentageFvc() {
        return percentageFvc;
    }

    public void setPercentageFvc(Double percentageFvc) {
        this.percentageFvc = percentageFvc;
    }

    @Basic
    @Column(name = "PREDICT_FEV", nullable = true, precision = 2)
    public Double getPredictFev() {
        return predictFev;
    }

    public void setPredictFev(Double predictFev) {
        this.predictFev = predictFev;
    }

    @Basic
    @Column(name = "PERCENTAGE_FEV", nullable = true, precision = 2)
    public Double getPercentageFev() {
        return percentageFev;
    }

    public void setPercentageFev(Double percentageFev) {
        this.percentageFev = percentageFev;
    }

    @Basic
    @Column(name = "PREDICT_FEV_FVC", nullable = true, precision = 2)
    public Double getPredictFevFvc() {
        return predictFevFvc;
    }

    public void setPredictFevFvc(Double predictFevFvc) {
        this.predictFevFvc = predictFevFvc;
    }

    @Basic
    @Column(name = "PERCENTAGE_FEV_FVC", nullable = true, precision = 2)
    public Double getPercentageFevFvc() {
        return percentageFevFvc;
    }

    public void setPercentageFevFvc(Double percentageFevFvc) {
        this.percentageFevFvc = percentageFevFvc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lung lung = (Lung) o;
        return Objects.equals(depId, lung.depId) && Objects.equals(tjrq, lung.tjrq) && Objects.equals(xj, lung.xj) && Objects.equals(createId, lung.createId) && Objects.equals(modifyId, lung.modifyId) && Objects.equals(id, lung.id) && Objects.equals(fvc, lung.fvc) && Objects.equals(fev, lung.fev) && Objects.equals(fevFvc, lung.fevFvc) && Objects.equals(percentageMmef, lung.percentageMmef) && Objects.equals(predictMmef, lung.predictMmef) && Objects.equals(mmef, lung.mmef) && Objects.equals(percentageFeffa, lung.percentageFeffa) && Objects.equals(predictFeffa, lung.predictFeffa) && Objects.equals(feffa, lung.feffa) && Objects.equals(percentageFeffb, lung.percentageFeffb) && Objects.equals(predictFeffb, lung.predictFeffb) && Objects.equals(feffb, lung.feffb) && Objects.equals(percentageFeffc, lung.percentageFeffc) && Objects.equals(predictFeffc, lung.predictFeffc) && Objects.equals(feffc, lung.feffc) && Objects.equals(patientcode, lung.patientcode) && Objects.equals(createdate, lung.createdate) && Objects.equals(modifydate, lung.modifydate) && Objects.equals(predictFvc, lung.predictFvc) && Objects.equals(percentageFvc, lung.percentageFvc) && Objects.equals(predictFev, lung.predictFev) && Objects.equals(percentageFev, lung.percentageFev) && Objects.equals(predictFevFvc, lung.predictFevFvc) && Objects.equals(percentageFevFvc, lung.percentageFevFvc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depId, tjrq, xj, createId, modifyId, id, fvc, fev, fevFvc, percentageMmef, predictMmef, mmef, percentageFeffa, predictFeffa, feffa, percentageFeffb, predictFeffb, feffb, percentageFeffc, predictFeffc, feffc, patientcode, createdate, modifydate, predictFvc, percentageFvc, predictFev, percentageFev, predictFevFvc, percentageFevFvc);
    }
}