package com.center.medical.statistics.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日体检量统计 页面下方总计数据
 */
@Data
public class EPTotalVo implements Serializable {
    private static final long serialVersionUID = 4447245843620137734L;

    @ApiModelProperty(value = "健康领取-全部")
    private Integer jklqAll;

    @ApiModelProperty(value = "健康领取-个人")
    private Integer jklqPersonal;

    @ApiModelProperty(value = "健康领取-团体")
    private Integer jklqOrg;

    @ApiModelProperty(value = "职业领取-全部")
    private Integer zylqAll;

    @ApiModelProperty(value = "职业领取-个人")
    private Integer zylqPersonal;

    @ApiModelProperty(value = "职业领取-团体")
    private Integer zylqOrg;

    @ApiModelProperty(value = "登记-全部")
    private Integer registAll;

    @ApiModelProperty(value = "登记-个人")
    private Integer regitsPersonal;

    @ApiModelProperty(value = "登记-团体")
    private Integer regitsOrg;

    @ApiModelProperty(value = "开始-全部")
    private Integer startAll;

    @ApiModelProperty(value = "开始-个人")
    private Integer startPersonal;

    @ApiModelProperty(value = "开始-团体")
    private Integer startOrg;

    @ApiModelProperty(value = "健康总检-全部")
    private Integer jkzjAll;

    @ApiModelProperty(value = "健康总检-个人")
    private Integer jkzjPersonal;

    @ApiModelProperty(value = "健康总检-团体")
    private Integer jkzjOrg;

    @ApiModelProperty(value = "职业总检-全部")
    private Integer zyzjAll;

    @ApiModelProperty(value = "职业总检-个人")
    private Integer zyzjPersonal;

    @ApiModelProperty(value = "职业总检-团体")
    private Integer zyzjOrg;
}
