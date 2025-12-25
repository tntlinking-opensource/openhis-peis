package com.center.medical.center.common.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@TableName("IntPatientExamItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntPatientExamItem implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="ID_IntPatientExamItem",type = IdType.AUTO)
    private Integer idIntPatientExamItem;// 主键，自增长
    @TableField("ID_PatientExamItem")
    private Integer idPatientExamItem;// 北斗用项目科室表示的
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
    @TableField("ExamItemRequestNo")
    private String examItemRequestNo;
    @TableField("ID_Depart")
    private Integer idDepart;// 标软科室编号  tiJianKeShi中的middlecode
    @TableField("Depart_Name")
    private String departName;// 标软科室名称 tiJianKeShi中的middlename
    @TableField("TransfterTarget")
    private String transfterTarget;// 标软项目类型    tiJianKeShi中的middleChuanSongMa
    @TableField("ID_ExamItem")
    private Integer idExamItem;// 标软检验标本类型  tiJanItemZuHe中的biaoBenType(名字不一样)
    @TableField("ExamItem_Name")
    private String examItemName;// 检验标本类型名称 目前表中未设置，估计用标本类型就不用名称(名字不一样)
    @TableField("ExamItem_Code")
    private String examItemCode;// 默认：null
    @TableField("F_Transfered")
    private Boolean FTransfered;// 0填充
    @TableField("F_Returned")
    private Boolean FReturned;// 0填充
    @TableField("F_ResultTransfered")
    private Boolean FResultTransfered;// 0填充
    @TableField("F_IsDeleted")
    private Boolean FIsDeleted;
    @TableField("DT_LastBySync")
    private Timestamp dtLastBySync;
    @TableField("DT_LastByPEIS")
    private Timestamp dtLastByPeis;// 修改时间  最后修改时间
    @TableField("DT_LastByHIS")
    private Timestamp dtLastByHis;
}