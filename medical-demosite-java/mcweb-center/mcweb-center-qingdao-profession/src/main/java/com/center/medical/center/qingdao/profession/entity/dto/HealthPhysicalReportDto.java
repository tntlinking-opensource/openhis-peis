package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 体检报告列表
 */
@Data
public class HealthPhysicalReportDto {

    @ApiModelProperty(value = "规则：机构编码+体检日期（yyyymmdd）体检号")
    private String documentId;

    @ApiModelProperty(value = "体检机构名称")
    private String phyExamName;

    @ApiModelProperty(value = "体检机构代码")
    private String phyExamCode;

    @ApiModelProperty(value = "体检机构所在地区")
    private String phyExamAddressName;

    @ApiModelProperty(value = "体检机构所在地区代码")
    private String phyExamAddressCode;

    @ApiModelProperty(value = "上传体检类型 1、职业查体 2、放射查体")
    private String physicalTypes;

    @ApiModelProperty(value = "体检日期 8 位时间：yyyyMMdd")
    private String examDate;

    @ApiModelProperty(value = "体检号")
    private String examNum;

    @ApiModelProperty(value = "姓名")
    private String workerName;

    @ApiModelProperty(value = "身份证件类型代码")
    private String idCardTypeCode;

    @ApiModelProperty(value = "身份证件号码")
    private String idCard;

    @ApiModelProperty(value = "性别代码")
    private String genderCode;

    @ApiModelProperty(value = "出生日期")
    private String birthDate;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "劳动者联系电话")
    private String workerTelphone;

    @ApiModelProperty(value = "体检类型代码")
    private String examTypeCode;

    @ApiModelProperty(value = "体检危害因素代码")
    private String factorCode;

    @ApiModelProperty(value = "其他危害因素具体名称")
    private String factorOther;

    @ApiModelProperty(value = "用人单位名称")
    private String enterpriseName;

    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "经济类型代码")
    private String economicTypeCode;

    @ApiModelProperty(value = "行业类别代码")
    private String industryCategoryCode;

    @ApiModelProperty(value = "企业规模代码")
    private String businessScaleCode;

    @ApiModelProperty(value = "所属地区名称")
    private String addressName;

    @ApiModelProperty(value = "所属地区代码")
    private String addressCode;

    @ApiModelProperty(value = "用人单位详细地址")
    private String addressDetail;

    @ApiModelProperty(value = "用人单位联系人")
    private String enterpriseContact;

    @ApiModelProperty(value = "单位联系电话")
    private String contactTelphone;

    @ApiModelProperty(value = "用人单位所在区全名称")
    private String allName;

    @ApiModelProperty(value = "用工单位名称")
    private String enterpriseNameEmployer;

    @ApiModelProperty(value = "用工单位统一社会信用代码")
    private String creditCodeEmployer;

    @ApiModelProperty(value = "用工单位企业类型代码")
    private String econocTypeCodeEmoyer;

    @ApiModelProperty(value = "用工单位行业类别代码")
    private String indtryCatryCodeEmoyer;

    @ApiModelProperty(value = "用工单位企业规模代码")
    private String busssScaleCodeEmoyer;

    @ApiModelProperty(value = "用工单位所属地区代码")
    private String addressCodeEmployer;

    @ApiModelProperty(value = "用工单位所在区全名称")
    private String allNameEmployer;

    @ApiModelProperty(value = "填表人名称")
    private String writePerson;

    @ApiModelProperty(value = "填表人电话")
    private String writePersonTelphone;

    @ApiModelProperty(value = "报告出具日期")
    private String writeDate;

    @ApiModelProperty(value = "检查单位名称")
    private String reportOrganCreditCode;

    @ApiModelProperty(value = "工种名称")
    private String workType;

    @ApiModelProperty(value = "工种代码")
    private String workTypeCode;

    @ApiModelProperty(value = "其他工种名称")
    private String otherWorkType;

    @ApiModelProperty(value = "监测类型代码")
    private String jcType;

    @ApiModelProperty(value = "开始接害日期")
    private String harmStartDate;

    @ApiModelProperty(value = "实际接害工龄-年")
    private String harmAgeYear;

    @ApiModelProperty(value = "实际接害工龄-月")
    private String harmAgeMonth;

    @ApiModelProperty(value = "是否复查")
    private String isReview;

    @ApiModelProperty(value = "报告人姓名")
    private String reportPerson;

    @ApiModelProperty(value = "报告人联系电话")
    private String reportPersonTel;

    @ApiModelProperty(value = "报告日期")
    private String reportDate;

    @ApiModelProperty(value = "接触的职业病危害因素代码")
    private String contactFactorCode;

    @ApiModelProperty(value = "接触的其他职业病危害因素")
    private String contactFactorOther;

    @ApiModelProperty(value = "数据操作类型Add,Update")
    private String operateType;

    @ApiModelProperty(value = "平台部门编号")
    private String deptId;

    @ApiModelProperty(value = "目前吸烟情况")
    private String smokingStatus;

    @ApiModelProperty(value = "吸烟史-年")
    private String personalHistoryYear;

    @ApiModelProperty(value = "吸烟史-月，小于等于 11 的整数")
    private String personalHistoryMonth;

    @ApiModelProperty(value = "平均每天吸烟量")
    private String dailySmokingVolume;

    @ApiModelProperty(value = "防护用品佩戴情况代码")
    private String protectiveEquipmentCode;
}
