package com.center.medical.reception.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检查名单 导出数据
 */
@Data
public class CheckListDto implements Serializable {
    private static final long serialVersionUID = -3180988733811655702L;

    @Excel(name = "错误行数")
    @ApiModelProperty(value = "错误行数")
    private String orderNum;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "婚姻")
    @ApiModelProperty(value = "婚姻")
    private String marriage;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @Excel(name = "组类")
    @ApiModelProperty(value = "组类")
    private String grouptype;

    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "统收限额")
    @Excel(name = "统收限额", cellType = Excel.ColumnType.NUMERIC)
    private Double tsLimit;

    @Excel(name = "错误信息")
    @ApiModelProperty(value = "检查备注")
    private String checkNote;


    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer idExamtype;


    @Excel(name = "原始工种", cellType = Excel.ColumnType.STRING)
    private String originalTrade;


    @Excel(name = "工种", cellType = Excel.ColumnType.STRING)
    private String trades;


    @ApiModelProperty(value = "接害因素")
    private String jhys;


    @ApiModelProperty(value = "体检类别：上岗前、在岗期间、离岗时、离岗后、应急")
    private String medicaltypeStr;
}
