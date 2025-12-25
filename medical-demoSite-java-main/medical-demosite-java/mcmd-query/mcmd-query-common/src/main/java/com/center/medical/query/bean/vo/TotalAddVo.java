package com.center.medical.query.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 加项情况查询 分页 返回数据
 */
@Data
public class TotalAddVo implements Serializable {
    private static final long serialVersionUID = -9079968226140762284L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "客户单位名称")
    private String orgName;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "收费人")
    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @Excel(name = "登记时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "项目科室")
    @ApiModelProperty(value = "科室名称")
    private String ksName;

    @Excel(name = "项目价格")
    @ApiModelProperty(value = "价格")
    private Double price;

    @Excel(name = "优惠价")
    @ApiModelProperty(value = "实付价格")
    private Double factprice;

//    @Excel(name = "金额")
//    @ApiModelProperty(value = "实付价格")
//    private Double addprice;

    @Excel(name = "加项医师")
    @ApiModelProperty(value = "加项医师")
    private String jxys;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String feeitemdesc;


}
