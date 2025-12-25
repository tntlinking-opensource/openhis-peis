package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 淮南体检者具体信息
 */
@Data
public class OhEmployees implements Serializable {
    private static final long serialVersionUID = 3426720053464902552L;


    @ApiModelProperty(value = "职业健康检查机构-编码")
    private String ohOrgCode;

    @ApiModelProperty(value = "用人单位名称")
    private String empOrgName;

    @ApiModelProperty(value = "用人单位统一社会信用代码")
    private String empOrgBizCode;

    @ApiModelProperty(value = "体检日期")
    private Date peDate;

    @ApiModelProperty(value = "初复检标识")
    private String peFlag;

    @ApiModelProperty(value = "姓名")
    private String patientName;

    @ApiModelProperty(value = "性别代码")
    private String sexCode;

    @ApiModelProperty(value = "证件类型")
    private String idType;

    @ApiModelProperty(value = "身份证号")
    private String idcard;

    @ApiModelProperty(value = "身份证号")
    private String empPhone;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContact;

    @ApiModelProperty(value = "紧急联系电话")
    private String emergencyContactNumber;

    @ApiModelProperty(value = "体检的职业病危害因素名称-名称")
    private String hazardName;

    @ApiModelProperty(value = "体检的其他危害因素的具体名称")
    private String hazardOther;

    @ApiModelProperty(value = "接触的危害因素代码")
    private String contactHazardCode;

    @ApiModelProperty(value = "接触的其他危害因素的具体名称")
    private String contactHazardOther;

    @ApiModelProperty(value = "工种-编码")
    private String typeOfWorkCode;

    @ApiModelProperty(value = "具体其他工种名称")
    private String typeOfWorkOther;

    @ApiModelProperty(value = "用工单位名称")
    private String employerEnterpriseName;

    @ApiModelProperty(value = "用工单位统一社会信用代码")
    private String employerCreditCode;

    @ApiModelProperty(value = "填表人姓名")
    private String preparerName;

    @ApiModelProperty(value = "填表人电话")
    private String preparerPhone;

    @ApiModelProperty(value = "报告出具日期")
    private Date preparerDate;

    @ApiModelProperty(value = "检查单位名称")
    private String reportOrgName;

    @ApiModelProperty(value = "备注")
    private String propose;

    @ApiModelProperty(value = "防护用品佩戴情况")
    private String protectiveEquipmentCode;


    @ApiModelProperty(value = "个人生活史")
    private PersonalHistoryDto personalHistory;

    @ApiModelProperty(value = "接触相关危害因素信息")
    private List<OhEmpHazardDto> ohEmpHazard;


    @ApiModelProperty(value = "体检项目结果")
    private List<OhItemClassResultVo> ohItemClassResult;
}
