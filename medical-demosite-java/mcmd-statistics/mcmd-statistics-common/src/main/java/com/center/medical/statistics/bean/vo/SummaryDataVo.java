package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康检查职业禁忌证人员名单 返回数据
 */
@Data
public class SummaryDataVo implements Serializable {
    private static final long serialVersionUID = -5234629327210084780L;

    @Excel(name = "单位名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "体检类别" ,readConverterExp = "0=上岗前,1=在岗期间,2=离岗时,3=离岗后,4=应急")
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @Excel(name = "接害工龄(年)")
    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @Excel(name = "毒物名称")
    @ApiModelProperty(value = "接害因素")
    private String jhys;


    @Excel(name = "疾病名称")
    @ApiModelProperty(value = "疾病名称")
    private String disease;

    @Excel(name = "职业阳性结果",readConverterExp = "null=非阳性,0=非阳性,1=阳性")
    @ApiModelProperty(value = "否阳性结果(1阳性  0或NULL非阳性)")
    private String posistive;

    @Excel(name = "报告单编号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;


}
