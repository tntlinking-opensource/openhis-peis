package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 导出应急导引单 返回数据
 */
@Data
public class ExportGuidanceListVo implements Serializable {
    private static final long serialVersionUID = 7878199376602525300L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "登记员")
    private String doctorreg;

    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "入职日期")
    private String dateinorganization;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "接害因素名称拼接")
    private String harms;

    @ApiModelProperty(value = "empty")
    private String empty;

    @ApiModelProperty(value = "支付方式拼接")
    private String payway;

    @ApiModelProperty(value = "收费项目名称")
    private List<String> examfeeitemName;

}
