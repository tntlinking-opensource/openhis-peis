package com.center.medical.reservation.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员预约记录
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@ApiModel(value = "ReservationData", description = "人员预约记录")
public class ReservationData implements Serializable {
    private static final long serialVersionUID = 2645551586995809403L;

    @ApiModelProperty(value = "分中心")
    @Excel(name = "分中心")
    private String branchName;

    @ApiModelProperty(value = "预约号")
    @Excel(name = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    @Excel(name = "订单类型", readConverterExp = "0=个检,1=团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "体检号")
    @Excel(name = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名")
    private String realName;

    @ApiModelProperty(value = "开单医生")
    @Excel(name = "开单医生")
    private String xsjl;

    @ApiModelProperty(value = "预约类型")
    @Excel(name = "预约类型")
    private String levelName;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    @Excel(name = "年龄")
    private Integer age;

    @ApiModelProperty(value = "证件号")
    @Excel(name = "证件号")
    private String idcard;

    @ApiModelProperty(value = "电话")
    @Excel(name = "电话")
    private String mobile;

    @ApiModelProperty(value = "单位")
    @Excel(name = "单位")
    private String khdwmc;

    @ApiModelProperty(value = "客户来源")
    @Excel(name = "客户来源")
    private String sourceNme;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
