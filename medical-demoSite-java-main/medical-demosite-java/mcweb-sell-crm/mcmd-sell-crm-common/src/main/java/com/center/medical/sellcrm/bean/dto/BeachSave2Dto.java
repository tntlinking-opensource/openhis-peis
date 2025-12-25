package com.center.medical.sellcrm.bean.dto;

import com.center.medical.common.annotation.Excel;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导入的名单信息
 */
@Data
public class BeachSave2Dto implements Serializable {
    private static final long serialVersionUID = -5648269330792215848L;


    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名", cellType = Excel.ColumnType.STRING)
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    @Excel(name = "身份证号", cellType = Excel.ColumnType.STRING)
    private String idcardno;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别", cellType = Excel.ColumnType.STRING)
    private String sexMale;

    @ApiModelProperty(value = "出生日期")
    @Excel(name = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "年龄")
    @Excel(name = "年龄", cellType = Excel.ColumnType.NUMERIC)
    private Integer age;

    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;

    @ApiModelProperty(value = "婚姻")
    @Excel(name = "婚姻", cellType = Excel.ColumnType.STRING)
    private String marriage;

    @ApiModelProperty(value = "文化程度")
    private Integer cultural;

    @ApiModelProperty(value = "文化程度")
    @Excel(name = "文化程度", cellType = Excel.ColumnType.STRING)
    private String culturalStr;

    @ApiModelProperty(value = "部门")
    @Excel(name = "部门", cellType = Excel.ColumnType.STRING)
    private String orgDepart;

    @ApiModelProperty(value = "原始工种")
    @Excel(name = "原始工种", cellType = Excel.ColumnType.STRING)
    private String originalTrade;

    @ApiModelProperty(value = "工种")
    @Excel(name = "工种", cellType = Excel.ColumnType.STRING)
    private String trades;

    @ApiModelProperty(value = "工种ID")
    private String worktypeId;

    @ApiModelProperty(value = "组类")
    @Excel(name = "组类", cellType = Excel.ColumnType.NUMERIC)
    private Integer grouptype;

    @ApiModelProperty(value = "接触（拟接触）危害因素")
    @Excel(name = "接触（拟接触）危害因素", cellType = Excel.ColumnType.STRING)
    private String jhysStr;

    @ApiModelProperty(value = "接触（拟接触）危害因素ID集合")
    private String jhys;

    @ApiModelProperty(value = "参加工作时间（非必填，用于计算总工龄）")
    @Excel(name = "参加工作时间（非必填，用于计算总工龄）")
    private Date workDate;

    @ApiModelProperty(value = "从事该岗位工种时间（非必填，用于计算接害工龄）")
    @Excel(name = "从事该岗位工种时间（非必填，用于计算接害工龄）")
    private Date harmDate;

    @ApiModelProperty(value = "总工龄年")
    @Excel(name = "总工龄年", cellType = Excel.ColumnType.NUMERIC)
    private Integer zgln;

    @ApiModelProperty(value = "总工龄月")
    @Excel(name = "总工龄月", cellType = Excel.ColumnType.NUMERIC)
    private Integer zgly;

    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @ApiModelProperty(value = "接害工龄年")
    @Excel(name = "接害工龄年", cellType = Excel.ColumnType.NUMERIC)
    private Integer jhgln;

    @ApiModelProperty(value = "接害工龄月")
    @Excel(name = "接害工龄月", cellType = Excel.ColumnType.NUMERIC)
    private Integer jhgly;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "体检类别：上岗前、在岗期间、离岗时、离岗后、应急")
    @Excel(name = "体检类别", cellType = Excel.ColumnType.STRING)
    private String medicaltypeStr;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer medicaltype;

    @ApiModelProperty(value = "联系电话")
    @Excel(name = "联系电话", cellType = Excel.ColumnType.STRING)
    private String phone;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer idExamtype;

    @ApiModelProperty(value = "体检类型：健康、职业、综合、复查")
    @Excel(name = "体检类型", cellType = Excel.ColumnType.STRING)
    private String examtype;

    @ApiModelProperty(value = "从事该岗位前接触何种危害因素")
    @Excel(name = "从事该岗位前接触何种危害因素", cellType = Excel.ColumnType.STRING)
    private String jhysBefore;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注", cellType = Excel.ColumnType.STRING)
    private String note;

    @ApiModelProperty(value = "统收限额")
    @Excel(name = "统收限额", cellType = Excel.ColumnType.NUMERIC)
    private Double tsLimit;

    @ApiModelProperty(value = "头像")
    private String picture;

    @ApiModelProperty(value = "订单id")
    private String idOrder;

    @ApiModelProperty(value = "团体任务对象")
    private Peisorgreservation vation;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "开单医师名称")
    private String doctorapply;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "销售经理ID")
    private String idOpenDoctor;

    @ApiModelProperty(value = "发放方式")
    private String idInformway;

    @ApiModelProperty(value = "登记员")
    private String idDoctorreg;

    @ApiModelProperty(value = "预约时间")
    private Date dateguidancereturned;

    @ApiModelProperty(value = "性别：0.男 1.女")
    private Integer idSex;

    @ApiModelProperty(value = "地区ID")
    private String idResarea;

    @ApiModelProperty(value = "地区码")
    private String resarea;
}
