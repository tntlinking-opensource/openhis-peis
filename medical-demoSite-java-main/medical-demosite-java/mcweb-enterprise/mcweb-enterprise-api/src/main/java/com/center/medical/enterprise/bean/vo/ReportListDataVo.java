package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检报告 分页返回数据
 */
@Data
public class ReportListDataVo implements Serializable {
    private static final long serialVersionUID = -239333096048313789L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "健康报告url")
    private String jkurl;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "结论")
    private String verdict;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @ApiModelProperty(value = "健康体检状态")
    private Integer jktjztNum;

    @ApiModelProperty(value = "职业体检状态")
    private Integer zytjztNum;

    @ApiModelProperty(value = "套餐名称")
    private String tc;

    @ApiModelProperty(value = "职业报告url")
    private String zyurl;

    @ApiModelProperty(value = "否阳性结果(1阳性  0或NULL非阳性)")
    private String posistive;

    @ApiModelProperty(value = "体检号")
    private String patientcode;




    @ApiModelProperty(value = "体检状态")
    private String tjzt;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "健康体检状态")
    private String jktjzt;

    @ApiModelProperty(value = "职业体检状态")
    private String zytjzt;

    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @ApiModelProperty(value = "处理意见")
    private String summaryText;
}
