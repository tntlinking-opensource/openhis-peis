package com.center.medical.center.qingdao.profession.entity.persistent;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "MD_NATION")
public class Nation {
    private String id;
    private String name;
    private String inputCode;
    private Date createdate;
    private Date modifydate;
    private String romeCode;
    private String wordCode;
    private String numberCode;

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
    @Column(name = "NAME", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "INPUT_CODE", nullable = true, length = 30)
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
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
    @Column(name = "ROME_CODE", nullable = true, length = 100)
    public String getRomeCode() {
        return romeCode;
    }

    public void setRomeCode(String romeCode) {
        this.romeCode = romeCode;
    }

    @Basic
    @Column(name = "WORD_CODE", nullable = true, length = 10)
    public String getWordCode() {
        return wordCode;
    }

    public void setWordCode(String wordCode) {
        this.wordCode = wordCode;
    }

    @Basic
    @Column(name = "NUMBER_CODE", nullable = true, length = 10)
    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nation nation = (Nation) o;
        return Objects.equals(id, nation.id) && Objects.equals(name, nation.name) && Objects.equals(inputCode, nation.inputCode) && Objects.equals(createdate, nation.createdate) && Objects.equals(modifydate, nation.modifydate) && Objects.equals(romeCode, nation.romeCode) && Objects.equals(wordCode, nation.wordCode) && Objects.equals(numberCode, nation.numberCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, inputCode, createdate, modifydate, romeCode, wordCode, numberCode);
    }
}