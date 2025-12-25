package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
/**
 * 销售月度目标分页返回数据
 */
@Data
public class MonthtargetVo implements Serializable {
    private static final long serialVersionUID = -5923109458427565726L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @Excel(name = "工作年龄")
    @ApiModelProperty(value = "工作年龄")
    private String workingAge;

    @Excel(name = "1月目标额（元）")
    @ApiModelProperty(value = "target1")
    private Double target1;

    @Excel(name = "1月实际完成额（元）")
    @ApiModelProperty(value = "1月实际完成额")
    private Double complete1;

    @Excel(name = "1月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "1月完成进度")
    private String completion1;

    @Excel(name = "2月目标额（元）")
    @ApiModelProperty(value = "target2")
    private Double target2;

    @Excel(name = "2月实际完成额（元）")
    @ApiModelProperty(value = "2月实际完成额")
    private Double complete2;

    @Excel(name = "2月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "2月完成进度")
    private String completion2;

    @Excel(name = "3月目标额（元）")
    @ApiModelProperty(value = "target3")
    private Double target3;

    @Excel(name = "3月实际完成额（元）")
    @ApiModelProperty(value = "3月实际完成额")
    private Double complete3;

    @Excel(name = "3月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "3月完成进度")
    private String completion3;

    @Excel(name = "4月目标额（元）")
    @ApiModelProperty(value = "target4")
    private Double target4;

    @Excel(name = "4月实际完成额（元）")
    @ApiModelProperty(value = "4月实际完成额")
    private Double complete4;

    @Excel(name = "4月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "4月完成进度")
    private String completion4;

    @Excel(name = "5月目标额（元）")
    @ApiModelProperty(value = "target5")
    private Double target5;

    @Excel(name = "5月实际完成额（元）")
    @ApiModelProperty(value = "5月实际完成额")
    private Double complete5;

    @Excel(name = "5月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "5月完成进度")
    private String completion5;

    @Excel(name = "6月目标额（元）")
    @ApiModelProperty(value = "target6")
    private Double target6;

    @Excel(name = "6月实际完成额（元）")
    @ApiModelProperty(value = "6月实际完成额")
    private Double complete6;

    @Excel(name = "6月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "6月完成进度")
    private String completion6;

    @Excel(name = "7月目标额（元）")
    @ApiModelProperty(value = "target7")
    private Double target7;

    @Excel(name = "7月实际完成额（元）")
    @ApiModelProperty(value = "7月实际完成额")
    private Double complete7;

    @Excel(name = "7月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "7月完成进度")
    private String completion7;

    @Excel(name = "8月目标额（元）")
    @ApiModelProperty(value = "target8")
    private Double target8;

    @Excel(name = "8月实际完成额（元）")
    @ApiModelProperty(value = "8月实际完成额")
    private Double complete8;

    @Excel(name = "8月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "8月完成进度")
    private String completion8;

    @Excel(name = "9月目标额（元）")
    @ApiModelProperty(value = "target9")
    private Double target9;

    @Excel(name = "9月实际完成额（元）")
    @ApiModelProperty(value = "9月实际完成额")
    private Double complete9;

    @Excel(name = "9月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "9月完成进度")
    private String completion9;

    @Excel(name = "10月目标额（元）")
    @ApiModelProperty(value = "target10")
    private Double target10;

    @Excel(name = "10月实际完成额（元）")
    @ApiModelProperty(value = "10实际完成额")
    private Double complete10;

    @Excel(name = "10月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "10月完成进度")
    private String completion10;

    @Excel(name = "11月目标额（元）")
    @ApiModelProperty(value = "target11")
    private Double target11;

    @Excel(name = "11月实际完成额（元）")
    @ApiModelProperty(value = "11月实际完成额")
    private Double complete11;

    @Excel(name = "11月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "11月完成进度")
    private String completion11;

    @Excel(name = "12月目标额（元）")
    @ApiModelProperty(value = "target12")
    private Double target12;

    @Excel(name = "12月实际完成额（元）")
    @ApiModelProperty(value = "12实际完成额")
    private Double complete12;

    @Excel(name = "12月完成进度")
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "12月完成进度")
    private String completion12;




























}
