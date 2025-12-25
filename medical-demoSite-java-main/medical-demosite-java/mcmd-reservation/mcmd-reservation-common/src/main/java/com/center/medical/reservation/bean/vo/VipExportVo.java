package com.center.medical.reservation.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * vip及贵宾导出 返回数据
 */
@Data
public class VipExportVo implements Serializable {
    private static final long serialVersionUID = 1212879934060661050L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "预约时间")
    private String reservationDate;

    @Excel(name = "预约来检时间段")
    @ApiModelProperty(value = "时间段")
    private String timeSlot;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String realName;

    @Excel(name = "性别",readConverterExp = "0=男,1=女,2=通用")
    @ApiModelProperty(value = "性别ID")
    private String sex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private String age;

    @Excel(name = "电话")
    @ApiModelProperty(value = "手机")
    private String mobile;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "单位")
    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @Excel(name = "预约类型",readConverterExp = "1=普通会员,2=vip,3=vvip")
    @ApiModelProperty(value = "预约类型ID")
    private String levelId;

    @Excel(name = "磁共振/胃肠镜预约时间")
    @ApiModelProperty(value = "磁共振/胃肠镜预约时间")
    private String yysj;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @Excel(name = "预约人")
    @ApiModelProperty(value = "预约人")
    private String yyr;
}
