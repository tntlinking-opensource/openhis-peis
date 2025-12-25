package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 来检客户统计 返回数据
 */
@Data
public class PhysicalExaminerVo implements Serializable {
    private static final long serialVersionUID = 2894173329068009262L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @Excel(name="登记时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @Excel(name="收费时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "收费时间")
    private Date chargdate;

    @Excel(name = "金额应付")
    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @Excel(name = "状态" ,readConverterExp = "0=未结账,1=已结账")
    @ApiModelProperty(value = "状态 0未结账1已结账")
    private Integer status;
}
