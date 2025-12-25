package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检者列表数据 返回数据
 */
@Data
public class PacsGetRankDataVo implements Serializable {
    private static final long serialVersionUID = -4747744734616400328L;

    @ApiModelProperty(value = "订单号")
    private Integer orderNo;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "会员类型")
    private String idPatientclass;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检类型")
    private String examtype;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "分检是否已审核")
    private String isAudit;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "sta")
    private String sta;

    @ApiModelProperty(value = "如果为1在科室显示红色名称")
    private String flag;

    @ApiModelProperty("vip")
    private String vip;

    @ApiModelProperty(value = "是否从标软提取")
    private Integer fdiffperson;

    @ApiModelProperty(value = "所有收费项目名称，逗号拼接")
    private String exams;

    @ApiModelProperty(value = "pacs登记页面已登记")
    private Double idExamplace;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

}
