package com.center.medical.reservation.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目预约明细 分页返回数据
 */
@Data
public class SortQueryVo implements Serializable {
    private static final long serialVersionUID = -7560184054592506276L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="预约日期")
    @ApiModelProperty(value = "预约日期")
    private String reservationDate;

    @Excel(name = "科室")
    @ApiModelProperty(value = "科室")
    private String dept;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费项目")
    private String item;

    @Excel(name = "体检形式" ,readConverterExp = "0=内检,1=外检")
    @ApiModelProperty(value = "体检形式：0 .内检 1.外检")
    private String sfwc;

    @Excel(name = "普通（上午）预约数量")
    @ApiModelProperty(value = "普通上午预约数量")
    private String am;

    @Excel(name = "普通（上午）上限")
    @ApiModelProperty(value = "普通上午上限")
    private String amlimit;

    @Excel(name = "普通（下午）预约数量")
    @ApiModelProperty(value = "普通下午预约数量")
    private String pm;

    @Excel(name = "普通（下午）上限")
    @ApiModelProperty(value = "普通下午上限")
    private String pmlimit;

    @Excel(name = "VIP预约数量")
    @ApiModelProperty(value = "vip预约数量")
    private String vip;

    @Excel(name = "VIP上限")
    @ApiModelProperty(value = "vip上限")
    private String viplimit;

    @Excel(name = "VVIP预约数量")
    @ApiModelProperty(value = "vvip预约数量")
    private String vvip;

    @Excel(name = "VVIP上限")
    @ApiModelProperty(value = "vvip上限")
    private String vviplimit;

}
