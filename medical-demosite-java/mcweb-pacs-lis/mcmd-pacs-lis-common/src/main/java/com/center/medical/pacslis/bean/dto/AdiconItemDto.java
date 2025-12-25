package com.center.medical.pacslis.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 艾迪康返回item实体类
 */
@Data
public class AdiconItemDto implements Serializable {
    private static final long serialVersionUID = 6172761280269040778L;

    private String adiconBarcode; // Adicon 条形码
    private String patientName; // 患者姓名
    private String sex; // 性别
    private String age; // 年龄
    private String ageType; // 年龄类型
    private String department; // 科室
    private String bedNo; // 床号
    private Date collectionDate; // 采集日期
    private LocalDateTime receivedDate; // 接收日期
    private Date lisDate; // LIS 日期
    private LocalDateTime reportDate; // 报告日期
    private String customerBarcode; // 客户条形码
    private String doctor; // 医生
    private String technician; // 技术员
    private String checkedBy; // 审核人
    private String remark; // 备注
    private String instrument; // 仪器
    private String serialNumber; // 序列号
    private String sampleType; // 样本类型
    private String clinicalDiagnosis; // 临床诊断
    private String itemCode; // 项目代码
    private String itemNameCn; // 项目名称（中文）
    private String itemNameEn; // 项目名称（英文）
    private String result; // 结果
    private String resultHint; // 结果提示
    private String resultReference; // 结果参考值
    private String resultUnit; // 结果单位
    private String testMethod; // 检测方法
    private String testMethodEn; // 检测方法（英文）
    private String sampleChar; // 样本特性
    private String resultPathology; // 结果病理
    private String patientNumber; // 患者编号
    private String patientPhone; // 患者电话
    private Date birthDate; // 出生日期
    private String jyjs; // 检验技术
    private String str1; // 字符1
    private String str2; // 字符2 pdf报告id
    private String str3; // 字符3
    private String str4; // 字符4
    private String str5; // 字符5
    private String customRequestItemId; // 自定义请求项目ID
    private String customItemId; // 自定义项目ID
    private String criticalValueFlag; // 危急值标志
    private String customerPositiveFlag; // 客户阳性标志
    private String xmbh; // 项目编号
    private String ybzt; // 医保状态
    private String pdf; // 报告 PDF 文件路径
    private String sfzy; // 是否专业
    private String bglxs; // 报告类型数
    private String zhmc; // 组合名称
    private String checkedById; // 审核人 ID
    private String technicianId; // 技术员 ID
    private String approverId; // 批准人 ID
    private String approver; // 批准人
    private String comboCode; // 组合代码


}
