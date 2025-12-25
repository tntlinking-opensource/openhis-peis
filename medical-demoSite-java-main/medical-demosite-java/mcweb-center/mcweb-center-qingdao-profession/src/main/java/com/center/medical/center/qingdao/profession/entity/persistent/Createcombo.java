package com.center.medical.center.qingdao.profession.entity.persistent;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_CREATECOMBO")
public class Createcombo {
    private String id;
    private String tjtcmc;
    private Integer tjlx;
    private String tjtcjc;
    private String tjtcsrm;
    private String jhys;
    private String zytjlb;
    private Integer syxb;
    private Double tcysjg;
    private Double tczk;
    private Double zhjg;
    private String khdwmc;
    private String khdwmcid;
    private String sfybd;
    private String sfyhtc;
    private String nlz;
    private String nld;
    private String fkfs;
    private Integer sl;
    private String jxj;
    private String combostate;
    private String xsjlid;
    private String fzxid;
    private Integer isDelete;
    private Date createdate;
    private Date modifydate;
    private String bz;
    private Integer bjzt;
    private Integer isPlus;
    private Integer tbzt;
    private Integer isActive;
    private Integer isRecommended;
    private Double comboSort;
    private Integer isBan;
    private String pinganId;

    @Id
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TJTCMC", nullable = true, length = 200)
    public String getTjtcmc() {
        return tjtcmc;
    }

    public void setTjtcmc(String tjtcmc) {
        this.tjtcmc = tjtcmc;
    }

    @Basic
    @Column(name = "TJLX", nullable = true, precision = 0)
    public Integer getTjlx() {
        return tjlx;
    }

    public void setTjlx(Integer tjlx) {
        this.tjlx = tjlx;
    }

    @Basic
    @Column(name = "TJTCJC", nullable = true, length = 200)
    public String getTjtcjc() {
        return tjtcjc;
    }

    public void setTjtcjc(String tjtcjc) {
        this.tjtcjc = tjtcjc;
    }

    @Basic
    @Column(name = "TJTCSRM", nullable = true, length = 100)
    public String getTjtcsrm() {
        return tjtcsrm;
    }

    public void setTjtcsrm(String tjtcsrm) {
        this.tjtcsrm = tjtcsrm;
    }

    @Basic
    @Column(name = "JHYS", nullable = true, length = 2900)
    public String getJhys() {
        return jhys;
    }

    public void setJhys(String jhys) {
        this.jhys = jhys;
    }

    @Basic
    @Column(name = "ZYTJLB", nullable = true, length = 100)
    public String getZytjlb() {
        return zytjlb;
    }

    public void setZytjlb(String zytjlb) {
        this.zytjlb = zytjlb;
    }

    @Basic
    @Column(name = "SYXB", nullable = true, precision = 0)
    public Integer getSyxb() {
        return syxb;
    }

    public void setSyxb(Integer syxb) {
        this.syxb = syxb;
    }

    @Basic
    @Column(name = "TCYSJG", nullable = true, precision = 3)
    public Double getTcysjg() {
        return tcysjg;
    }

    public void setTcysjg(Double tcysjg) {
        this.tcysjg = tcysjg;
    }

    @Basic
    @Column(name = "TCZK", nullable = true, precision = 3)
    public Double getTczk() {
        return tczk;
    }

    public void setTczk(Double tczk) {
        this.tczk = tczk;
    }

    @Basic
    @Column(name = "ZHJG", nullable = true, precision = 3)
    public Double getZhjg() {
        return zhjg;
    }

    public void setZhjg(Double zhjg) {
        this.zhjg = zhjg;
    }

    @Basic
    @Column(name = "KHDWMC", nullable = true, length = 200)
    public String getKhdwmc() {
        return khdwmc;
    }

    public void setKhdwmc(String khdwmc) {
        this.khdwmc = khdwmc;
    }

    @Basic
    @Column(name = "KHDWMCID", nullable = true, length = 32)
    public String getKhdwmcid() {
        return khdwmcid;
    }

    public void setKhdwmcid(String khdwmcid) {
        this.khdwmcid = khdwmcid;
    }

    @Basic
    @Column(name = "SFYBD", nullable = true, length = 20)
    public String getSfybd() {
        return sfybd;
    }

    public void setSfybd(String sfybd) {
        this.sfybd = sfybd;
    }

    @Basic
    @Column(name = "SFYHTC", nullable = true, length = 20)
    public String getSfyhtc() {
        return sfyhtc;
    }

    public void setSfyhtc(String sfyhtc) {
        this.sfyhtc = sfyhtc;
    }

    @Basic
    @Column(name = "NLZ", nullable = true, length = 10)
    public String getNlz() {
        return nlz;
    }

    public void setNlz(String nlz) {
        this.nlz = nlz;
    }

    @Basic
    @Column(name = "NLD", nullable = true, length = 10)
    public String getNld() {
        return nld;
    }

    public void setNld(String nld) {
        this.nld = nld;
    }

    @Basic
    @Column(name = "FKFS", nullable = true, length = 20)
    public String getFkfs() {
        return fkfs;
    }

    public void setFkfs(String fkfs) {
        this.fkfs = fkfs;
    }

    @Basic
    @Column(name = "SL", nullable = true, precision = 0)
    public Integer getSl() {
        return sl;
    }

    public void setSl(Integer sl) {
        this.sl = sl;
    }

    @Basic
    @Column(name = "JXJ", nullable = true, length = 50)
    public String getJxj() {
        return jxj;
    }

    public void setJxj(String jxj) {
        this.jxj = jxj;
    }

    @Basic
    @Column(name = "COMBOSTATE", nullable = true, length = 50)
    public String getCombostate() {
        return combostate;
    }

    public void setCombostate(String combostate) {
        this.combostate = combostate;
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
    @Column(name = "FZXID", nullable = true, length = 2950)
    public String getFzxid() {
        return fzxid;
    }

    public void setFzxid(String fzxid) {
        this.fzxid = fzxid;
    }

    @Basic
    @Column(name = "IS_DELETE", nullable = true, precision = 0)
    public Integer getDelete() {
        return isDelete;
    }

    public void setDelete(Integer delete) {
        isDelete = delete;
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
    @Column(name = "BZ", nullable = true, length = 2000)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Basic
    @Column(name = "BJZT", nullable = true, precision = 0)
    public Integer getBjzt() {
        return bjzt;
    }

    public void setBjzt(Integer bjzt) {
        this.bjzt = bjzt;
    }

    @Basic
    @Column(name = "IS_PLUS", nullable = true, precision = 0)
    public Integer getPlus() {
        return isPlus;
    }

    public void setPlus(Integer plus) {
        isPlus = plus;
    }

    @Basic
    @Column(name = "TBZT", nullable = true, precision = 0)
    public Integer getTbzt() {
        return tbzt;
    }

    public void setTbzt(Integer tbzt) {
        this.tbzt = tbzt;
    }

    @Basic
    @Column(name = "IS_ACTIVE", nullable = true, precision = 0)
    public Integer getActive() {
        return isActive;
    }

    public void setActive(Integer active) {
        isActive = active;
    }

    @Basic
    @Column(name = "IS_RECOMMENDED", nullable = true, precision = 0)
    public Integer getRecommended() {
        return isRecommended;
    }

    public void setRecommended(Integer recommended) {
        isRecommended = recommended;
    }

    @Basic
    @Column(name = "COMBO_SORT", nullable = true, precision = 0)
    public Double getComboSort() {
        return comboSort;
    }

    public void setComboSort(Double comboSort) {
        this.comboSort = comboSort;
    }

    @Basic
    @Column(name = "IS_BAN", nullable = true, precision = 0)
    public Integer getBan() {
        return isBan;
    }

    public void setBan(Integer ban) {
        isBan = ban;
    }

    @Basic
    @Column(name = "PINGAN_ID", nullable = true, length = 64)
    public String getPinganId() {
        return pinganId;
    }

    public void setPinganId(String pinganId) {
        this.pinganId = pinganId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Createcombo that = (Createcombo) o;
        return Objects.equals(id, that.id) && Objects.equals(tjtcmc, that.tjtcmc) && Objects.equals(tjlx, that.tjlx) && Objects.equals(tjtcjc, that.tjtcjc) && Objects.equals(tjtcsrm, that.tjtcsrm) && Objects.equals(jhys, that.jhys) && Objects.equals(zytjlb, that.zytjlb) && Objects.equals(syxb, that.syxb) && Objects.equals(tcysjg, that.tcysjg) && Objects.equals(tczk, that.tczk) && Objects.equals(zhjg, that.zhjg) && Objects.equals(khdwmc, that.khdwmc) && Objects.equals(khdwmcid, that.khdwmcid) && Objects.equals(sfybd, that.sfybd) && Objects.equals(sfyhtc, that.sfyhtc) && Objects.equals(nlz, that.nlz) && Objects.equals(nld, that.nld) && Objects.equals(fkfs, that.fkfs) && Objects.equals(sl, that.sl) && Objects.equals(jxj, that.jxj) && Objects.equals(combostate, that.combostate) && Objects.equals(xsjlid, that.xsjlid) && Objects.equals(fzxid, that.fzxid) && Objects.equals(isDelete, that.isDelete) && Objects.equals(createdate, that.createdate) && Objects.equals(modifydate, that.modifydate) && Objects.equals(bz, that.bz) && Objects.equals(bjzt, that.bjzt) && Objects.equals(isPlus, that.isPlus) && Objects.equals(tbzt, that.tbzt) && Objects.equals(isActive, that.isActive) && Objects.equals(isRecommended, that.isRecommended) && Objects.equals(comboSort, that.comboSort) && Objects.equals(isBan, that.isBan) && Objects.equals(pinganId, that.pinganId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tjtcmc, tjlx, tjtcjc, tjtcsrm, jhys, zytjlb, syxb, tcysjg, tczk, zhjg, khdwmc, khdwmcid, sfybd, sfyhtc, nlz, nld, fkfs, sl, jxj, combostate, xsjlid, fzxid, isDelete, createdate, modifydate, bz, bjzt, isPlus, tbzt, isActive, isRecommended, comboSort, isBan, pinganId);
    }
}