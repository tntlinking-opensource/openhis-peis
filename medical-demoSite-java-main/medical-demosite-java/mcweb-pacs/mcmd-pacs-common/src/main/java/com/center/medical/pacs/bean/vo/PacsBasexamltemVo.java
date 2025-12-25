package com.center.medical.pacs.bean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xhp
 * @since 2023-03-29 8:06
 */
public class PacsBasexamltemVo {

    @ApiModelProperty(value = "pacs检查项目id")
    private String id;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "性别：0.代表男 1.代表女 2.通用")
    private Integer fMale;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "类型：0.健康检查类型 1.职业检查类型 2.健康+职业")
    private Integer type;

    @ApiModelProperty(value = "科室编号")
    private String divisionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamitemName() {
        return examitemName;
    }

    public void setExamitemName(String examitemName) {
        this.examitemName = examitemName;
    }

    public Integer getfMale() {
        return fMale;
    }

    public void setfMale(Integer fMale) {
        this.fMale = fMale;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }
}
