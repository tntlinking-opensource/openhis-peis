package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目检况查询 分页返回数据
 */
@Data
public class StatusQueryVo implements Serializable {
    private static final long serialVersionUID = -6589591520151793575L;

    @Excel(name = "体检类型", readConverterExp = "0=健康,1=职业,2综合,3复查")
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @Excel(name="登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别0男1女")
    private String idSex;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "类型")
    @ApiModelProperty(value = "类型,拼接字符串")
    private String types;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "公司")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;


    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价")
    private Double price;


    @Excel(name = "优惠价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "优惠价")
    private Double factprice;

    @ApiModelProperty(value = "补检状态 0: 未补检 1：已补检")
    private Integer fTransferedhl7;

    @ApiModelProperty(value = "弃检")
    private Integer fGiveup;

    @ApiModelProperty(value = "是否迟检：0.否 1.是")
    private Integer fDelayed;

    @ApiModelProperty(value = "据检状态  1据检   null未据检")
    private Integer sfjj;

    @ApiModelProperty(value = "是否检查：0：未检；1：已检；")
    private Integer fExaminated;



}
