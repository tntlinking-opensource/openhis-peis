package com.center.medical.pacs.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@TableName("IntPatientFeeItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntPatientFeeItem implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="ID_IntPatientFeeItem",type = IdType.AUTO)
    private Integer idIntPatientFeeItem;// 主键，自增长
    @TableField("ID_PatientFeeItem")
    private Integer idPatientFeeItem;// 收费项目号（？）
    @TableField("HospitalCode")
    private String hospitalCode;// 医院编码 默认：SDQDZK
    @TableField("ID_Patient")
    private Integer idPatient;// 8位登记号检验科lis软件窗口最多识别8位，多余的将截断  数据接口主要字段
    @TableField("StrIDPatient")
    private String strIdpatient;// 登记号
    @TableField("PatientCode")
    private String patientCode;// 登记号
    @TableField("PatientCodeHiden")
    private String patientCodeHiden;// 时间+流水号   可为年月日+流水号（5位）标软4位北斗6位
    @TableField("PatientName")
    private String patientName;// 姓名    体检者姓名
    @TableField("FeeType_Name")
    private String feeTypeName;//外送机构名称
    @TableField("FeeType_Code")
    private String feeTypeCode;//外送机构id
    @TableField("FeeItemRequestNo")
    private String feeItemRequestNo;
    @TableField("ID_Depart")
    private Integer idDepart;// 标软科室编号  tiJianKeShi中的middlecode
    @TableField("Depart_Name")
    private String departName;// 标软科室名称 tiJianKeShi中的middlename
    @TableField("TransfterTarget")
    private String transfterTarget;// 标软科室类型    tiJianKeShi中的middleChuanSongMa
    @TableField("ID_ExamFeeItem")
    private Integer idExamFeeItem;// 标软项目ID tiJanItemZuHe中的middleid
    @TableField("ExamFeeItem_Name")
    private String examFeeItemName;// 标软项目名称    tiJanItemZuHe中的name
    @TableField("ExamFeeItem_Code")
    private String examFeeItemCode;// 标软项目代码    tiJanItemZuHe中的middlecode
    @TableField("ExamFeeItem_Code2")
    private String examFeeItemCode2;
    @TableField("ExamFeeItem_Code3")
    private String examFeeItemCode3;
    @TableField("ExamFeeItem_FeeCode")
    private String examFeeItemFeeCode;
    @TableField("ExamFeeItem_FeeCodeSub")
    private String examFeeItemFeeCodeSub;
    @TableField("Qty")
    private Double qty;// 默认：0.00
    @TableField("UnitPrice")
    private Double unitPrice;// 原价
    @TableField("FactPrice")
    private Double factPrice;// 实际收费价格
    @TableField("Specification")
    private String specification;
    @TableField("MeasureUnit")
    private String measureUnit;
    @TableField("ID_LabType")
    private Integer idLabType;// 检验标本类型 tiJanItemZuHe中的biaoBenType
    @TableField("LabType_Name")
    private String labTypeName;// 检验标本类型名称  目前表中未设置，估计用标本类型就不用名称
    @TableField("LabType_Code")
    private String labTypeCode;//标本种类名称
    @TableField("LabType_Sub")
    private String labTypeSub;
    @TableField("LabType_Note")
    private String labTypeNote;
    @TableField("HisOpenDepartName")
    private String hisOpenDepartName;
    @TableField("HisOpenDepartCode")
    private String hisOpenDepartCode;
    @TableField("HisExecDepartName")
    private String hisExecDepartName;
    @TableField("HisExecDepartCode")
    private String hisExecDepartCode;
    @TableField("HisExecWorkGroupName")
    private String hisExecWorkGroupName;
    @TableField("HisExecWorkGroupCode")
    private String hisExecWorkGroupCode;
    @TableField("HisExecWorkStationName")
    private String hisExecWorkStationName;
    @TableField("HisExecWorkStationCode")
    private String hisExecWorkStationCode;
    @TableField("HisExecWorkBenchName")
    private String hisExecWorkBenchName;
    @TableField("HisExecWorkBenchCode")
    private String hisExecWorkBenchCode;
    @TableField("F_FeeCharged")
    private Boolean FFeeCharged;// 收费标志 默认：1，表示此项目接口已处理
    @TableField("F_Transfered")
    private Boolean FTransfered;// 核收标志 默认0未核收，1已核收
    @TableField("F_Returned")
    private Boolean FReturned;// 退项退费标志 默认为0，1时表示已被退项退费
    @TableField("F_IsDeleted")
    private Boolean FIsDeleted;
    @TableField("F_Back_Transfered")
    private Boolean FBackTransfered;
    @TableField("F_Back_ExamStarted")
    private Boolean FBackExamStarted;
    @TableField("F_Back_ExamFinished")
    private Boolean FBackExamFinished;
    @TableField("F_ResultTransfered")
    private Boolean FResultTransfered;// 结果传输标志 默认为0，1时表示检查结果已被取回
    @TableField("DT_LastBySync")
    private Timestamp dtLastBySync;
    @TableField("DT_LastByPEIS")
    private Timestamp dtLastByPeis;// 修改时间  最后修改时间
    @TableField("DT_LastByHIS")
    private Timestamp dtLastByHis;
    @TableField("F_TransferedOK")
    private Boolean FTransferedOk;// 默认NULL，不用传
    @TableField("TransferRetryTimes")
    private Integer transferRetryTimes;
}