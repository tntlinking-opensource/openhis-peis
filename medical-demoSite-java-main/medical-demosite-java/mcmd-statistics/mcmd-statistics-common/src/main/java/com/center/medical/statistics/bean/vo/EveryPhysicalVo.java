package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日体检量统计 返回数据
 */
@Data
public class EveryPhysicalVo implements Serializable {
    private static final long serialVersionUID = 3574783792271107237L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "日期")
    private String date;

    @Excel(name = "分中心ID")
    @ApiModelProperty(value = "周几")
    private Integer dayForWeek;

    @Excel(name = "个人登记数")
    @ApiModelProperty(value = "登记-个人")
    private String regitsPersonal;

    @Excel(name = "团体登记数")
    @ApiModelProperty(value = "登记-团体")
    private String regitsOrg;

    @Excel(name = "登记总人数")
    @ApiModelProperty(value = "登记-全部")
    private String registAll;

    @Excel(name = "个人开检人数")
    @ApiModelProperty(value = "开始-个人")
    private String startPersonal;

    @Excel(name = "团体开检数")
    @ApiModelProperty(value = "开始-团体")
    private String startOrg;

    @Excel(name = "开检总人数")
    @ApiModelProperty(value = "开始-全部")
    private String startAll;

    @Excel(name = "职业个人总检数")
    @ApiModelProperty(value = "职业总检-个检")
    private String zyzjPersonal;

    @Excel(name = "职业团体总检数")
    @ApiModelProperty(value = "职业总检-团检")
    private String zyzjOrg;

    @Excel(name = "职业总检总人数")
    @ApiModelProperty(value = "职业总检-全部")
    private String zyzjAll;

    @Excel(name = "健康个人总检数")
    @ApiModelProperty(value = "健康总检-个人")
    private String jkzjPersonal;

    @Excel(name = "健康团体总检数")
    @ApiModelProperty(value = "健康总检-团体")
    private String jkzjOrg;

    @Excel(name = "健康总检总人数")
    @ApiModelProperty(value = "健康总检-全部")
    private String jkzjAll;

    @Excel(name = "职业个人领取数")
    @ApiModelProperty(value = "职业领取-个人")
    private String zylqPersonal;

    @Excel(name = "职业团体领取数")
    @ApiModelProperty(value = "职业领取-团检")
    private String zylqOrg;

    @Excel(name = "职业领取总份数")
    @ApiModelProperty(value = "职业领取-全部")
    private String zylqAll;

    @Excel(name = "健康个人领取数")
    @ApiModelProperty(value = "健康领取-个检")
    private String jklqPersonal;

    @Excel(name = "健康团体领取数")
    @ApiModelProperty(value = "健康领取-团检")
    private String jklqOrg;

    @Excel(name = "健康领取总份数")
    @ApiModelProperty(value = "健康领取-全部")
    private String jklqAll;


    @ApiModelProperty(value = "登记人数总金额")
    private String registAllMoney;

    @ApiModelProperty(value = "开始体检总金额")
    private String startAllMoney;

    @ApiModelProperty(value = "健康总检总金额")
    private String jkzjMoney;

    @ApiModelProperty(value = "职业总检总金额")
    private String zyzjMoney;

    @ApiModelProperty(value = "健康领取总金额")
    private String jklqMoney;

    @ApiModelProperty(value = "职业领取总金额")
    private String zylqMoney;
}
