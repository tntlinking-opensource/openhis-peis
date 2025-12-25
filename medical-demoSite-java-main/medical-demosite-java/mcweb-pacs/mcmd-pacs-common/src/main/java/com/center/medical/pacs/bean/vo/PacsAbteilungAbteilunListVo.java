package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**科室列表
 * @author xhp
 * @since 2023-03-22 8:22
 */
public class PacsAbteilungAbteilunListVo {
    @ApiModelProperty(value = "部门编号")
    private String deptNo;
    @ApiModelProperty(value = "部门名称")
    private String deptName;
    @ApiModelProperty(value = "数据报告格式")
    private Integer sjbggs;
    @ApiModelProperty(value = "图片地址")
    private String imgpath;
    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;
    @ApiModelProperty(value = "接口类型")
    private String jklx;

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getSjbggs() {
        return sjbggs;
    }

    public void setSjbggs(Integer sjbggs) {
        this.sjbggs = sjbggs;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public Integer getfExaminated() {
        return fExaminated;
    }

    public void setfExaminated(Integer fExaminated) {
        this.fExaminated = fExaminated;
    }

    public String getJklx() {
        return jklx;
    }

    public void setJklx(String jklx) {
        this.jklx = jklx;
    }
}
