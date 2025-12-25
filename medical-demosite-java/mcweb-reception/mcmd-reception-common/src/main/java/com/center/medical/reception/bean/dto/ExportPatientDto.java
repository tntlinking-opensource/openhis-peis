package com.center.medical.reception.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 备单导出体检者信息
 */
@Data
public class ExportPatientDto implements Serializable {
    private static final long serialVersionUID = 3033356411360237549L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "任务名称")
    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "手机号码")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name="出生日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @Excel(name="身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "婚姻" ,readConverterExp = "1=未婚,2=已婚,3=离异,4=丧偶,5=其他")
    @ApiModelProperty(value = "婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他")
    private Integer idMarriage;

    @Excel(name="民族")
    @ApiModelProperty(value = "民族")
    private String nation;

    @Excel(name="文化程度")
    @ApiModelProperty(value = "文化水平，详见：com.center.medical.bean.enums.CulturalLevel")
    private String cultural;

    @Excel(name="部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name="工号")
    @ApiModelProperty(value = "工号")
    private String workno;

    @Excel(name="工种")
    @ApiModelProperty(value = "工种")
    private String trades;

    @Excel(name="参加工作时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "参加工作时间")
    private Date workDate;

    @Excel(name="总工龄(月)")
    @ApiModelProperty(value = "总工龄")
    private Integer zgl;

    @Excel(name="从事工种时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "从事该工种时间")
    private Date harmDate;

    @Excel(name="接害工龄(月)")
    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @Excel(name="体检类别")
    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @Excel(name="备注")
    @ApiModelProperty(value = "备注")
    private String note;


}
