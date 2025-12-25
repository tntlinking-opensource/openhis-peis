package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-03-29 8:06
 */
public class PacsItemExamListVo {
    @ApiModelProperty(value = "收费项目和检查项目关联表id")
    private String id;

    @ApiModelProperty(value = "检查项目ID")
    private String inspectId;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "性别：0.代表男 1.代表女 2.通用")
    private Integer fMale;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "类型：0.健康检查类型 1.职业检查类型 2.健康+职业")
    private Integer type;

    @ApiModelProperty(value = "排序")
    private Integer orderIndex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInspectId() {
        return inspectId;
    }

    public void setInspectId(String inspectId) {
        this.inspectId = inspectId;
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

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
