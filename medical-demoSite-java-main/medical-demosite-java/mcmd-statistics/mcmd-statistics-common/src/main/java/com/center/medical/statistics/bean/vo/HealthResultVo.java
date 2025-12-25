package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 健康检查结果附表 分页返回数据
 */
@Data
public class HealthResultVo implements Serializable {
    private static final long serialVersionUID = -3889014091442293675L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "登记时间")
    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String sex;


    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "综述")
    @ApiModelProperty(value = "综述")
    private String summarize;

    @Excel(name = "健康建议")
    @ApiModelProperty(value = "健康总检：健康建议；职业总检：职业结论及建议 OFFER")
    private String offer;


    @Excel(name = "体检单位")
    @ApiModelProperty(value = "团体名称")
    private String org;



}
