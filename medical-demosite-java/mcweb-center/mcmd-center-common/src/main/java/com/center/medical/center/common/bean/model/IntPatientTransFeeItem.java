package com.center.medical.center.common.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

//@ApiModel("中间库")
@TableName("IntPatientTransFeeItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntPatientTransFeeItem implements java.io.Serializable {
    @TableId(value="ID_PatientTransFeeItem",type = IdType.AUTO)
    private Integer idPatientTransFeeItem;// 主键，自增长
    @TableField("HospitalCode")
    private String hospitalCode;// 医院编码 默认：SDQDZK
    @TableField("ID_PatientTraceExamDepart")
    private Integer idPatientTraceExamDepart;// 一个体检号对应一个（可能为收款记录号）
    @TableField("ID_PatientFeeItem")
    private Integer idPatientFeeItem;// 收费项目号（？）
    @TableField("ID_Depart")
    private Integer idDepart;// 标软所属科室ID    tiJianKeShi中的middlecode
    @TableField("ID_Patient")
    private Integer idPatient;// 8位登记号检验科lis软件窗口最多识别8位，多余的将截断  数据接口主要字段
    @TableField("PatientCode")
    private String patientCode;// 登记号
    @TableField("ID_ExamFeeItemTrans")
    private Integer idExamFeeItemTrans;// 标软项目ID    tiJanItemZuHe中的middleid
    @TableField("ExamFeeItemTrans_Name")
    private String examFeeItemTransName;// 标软项目名称   tiJanItemZuHe中的name
    @TableField("ExamFeeItemTrans_Code")
    private String examFeeItemTransCode;// 标软项目代码   tiJanItemZuHe中的middlecode
    @TableField("UnitPrice")
    private Double unitPrice;// 价原
    @TableField("FactPrice")
    private Double factPrice;// 实际收费价格
    @TableField("ID_FeeCharger")
    private Integer idFeeCharger;// 操作员编号
    @TableField("FeeCharger")
    private String feeCharger;// 操作员名称
    @TableField("FeeChargerCode")
    private String feeChargerCode;// 操作员代码
    @TableField("FeeChargeTime")
    private Timestamp feeChargeTime;// 收款时间
    @TableField("Qty")
    private Double qty;// 去退费？  -1.00退费，北斗未处理，全用的默认1.00
    @TableField("ApprovedDate")
    private Timestamp approvedDate;// 确认核准日期？   不确定，北斗同收款时间一样
    @TableField("ApprovedTime")
    private Timestamp approvedTime;// 确认核准时间？   不确定，北斗同收款时间一样
    @TableField("DepartRegCode")
    private String departRegCode;// 默认：TJ
    @TableField("DepartRegName")
    private String departRegName;// 默认：体检中心
    @TableField("DepartExeCode")
    private String departExeCode;// 默认：TJ
    @TableField("DepartExeName")
    private String departExeName;// 默认：体检中心
    @TableField("ID_DepartApprovedBy")
    private Integer idDepartApprovedBy;
    @TableField("DepartApprovedBy_Name")
    private String departApprovedByName;
    @TableField("DepartApprovedBy_Code")
    private String departApprovedByCode;
    @TableField("F_Transfered")
    private Integer FTransfered;
}