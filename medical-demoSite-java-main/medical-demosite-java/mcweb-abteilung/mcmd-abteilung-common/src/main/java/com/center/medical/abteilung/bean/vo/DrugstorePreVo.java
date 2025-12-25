package com.center.medical.abteilung.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 药房管理-审核发药分页返回数据
 */
@Data
public class DrugstorePreVo implements Serializable {
    private static final long serialVersionUID = -1317394522227998565L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机")
    private String phone;

    @Excel(name = "开药医师")
    @ApiModelProperty(value = "开药医生")
    private String username;

    @Excel(name = "开药时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开药时间")
    private Date prescribeTime;


    @Excel(name = "药品")
    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @Excel(name = "状态" ,readConverterExp = "0=否,1=已成交,2=未成交,3=退药")
    @ApiModelProperty(value = "是否已开药：0.否 1.已成交 2.未成交 3.退药")
    private Integer isFinished;

    @ApiModelProperty(value = "开药原因")
    private String reason;

    @Excel(name = "成交价")
    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "数量")
    private String prescribeNum;

    @ApiModelProperty(value = "规格")
    private String drugStandard;

    @ApiModelProperty(value = "药品单价")
    private Double drugPrice;


    @Excel(name = "成本价")
    @ApiModelProperty(value = "成本价格")
    private Double costPrice;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "成本价相加，老系统的price.costPrice")
    private Double SumCostPrice;

    @ApiModelProperty(value = "价格相加，老系统的price.price")
    private Double SumPrice;

    @ApiModelProperty(value = "药品id")
    private String drugId;
}
