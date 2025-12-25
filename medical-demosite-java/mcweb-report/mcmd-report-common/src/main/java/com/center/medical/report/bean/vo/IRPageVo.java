package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验报告分页返回数据
 */
@Data
public class IRPageVo implements Serializable {
    private static final long serialVersionUID = -5384771737441514297L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "打印次数")
    private Integer printCount;

    @ApiModelProperty(value = "最近打印时间")
    private Date printTime;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;


}
