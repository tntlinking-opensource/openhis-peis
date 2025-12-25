package com.center.medical.center.common.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("IntPatientTransTarget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntPatientTransTarget implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="ID_IntPatientTransTarget",type = IdType.AUTO)
    private Integer idIntPatientTransTarget;// 主键，自增长
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
    @TableField("FeeItemList")
    private String feeItemList;// 收费项目列表    根据TransfterTarget, ID_LabType来分纪录
    @TableField("ID_TransDepart")
    private Integer idTransDepart;
    @TableField("TransfterTarget")
    private String transfterTarget;// 项目类型  tiJianKeShi中的middleChuanSongMa
    @TableField("ID_LabType")
    private Integer idLabType;// 检验标本类型 tiJanItemZuHe中的biaoBenType
    @TableField("LabType_Name")
    private String labTypeName;// 检验标本类型名称  目前表中未设置，估计用标本类型就不用名称
    @TableField("LabType_Code")
    private String labTypeCode;// 默认：null
    @TableField("F_Transfered")
    private Boolean FTransfered;// 0填充
    @TableField("F_Returned")
    private Boolean FReturned;// 0填充
    @TableField("F_ResultTransfered")
    private Boolean FResultTransfered;// 0填充
}