package com.center.medical.pacslis.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xhp
 * @since 2023-02-28 11:19
 */
@ApiModel("插入中间库接口固定参数对象-人员信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiddleDbPatientDto {
    @ApiModelProperty(value = "身份证号")
    private String idcardNo;
    @ApiModelProperty(value = "姓名")
    private String patientName;
    @ApiModelProperty(value = "会员等级")
    private Integer idPatientclass;
    @ApiModelProperty(value = "档案号")
    private Integer idPatientArchive;
    @ApiModelProperty(value = "拼音码")
    private String inputCode;
    @ApiModelProperty(value = "性别  男、女")
    private String sex;
    @ApiModelProperty(value = "出生日期")
    private Timestamp birthDate;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "婚姻状况")
    private String marriage;
    @ApiModelProperty(value = "家庭住址")
    private String resAddress;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "单位名称")
    private String orgName;
    @ApiModelProperty(value = "登记员")
    private String doctorReg;
    @ApiModelProperty(value = "体检类型名称")
    private String examTypeName;
    @ApiModelProperty(value = "销售员名称")
    private String doctorOpen;
    @ApiModelProperty(value = "短体检号")
    private Integer idPatient;
    @ApiModelProperty(value = "体检号")
    private String strIdpatient;
    @ApiModelProperty(value = "8位登记号")
    private String patientCode;
}
