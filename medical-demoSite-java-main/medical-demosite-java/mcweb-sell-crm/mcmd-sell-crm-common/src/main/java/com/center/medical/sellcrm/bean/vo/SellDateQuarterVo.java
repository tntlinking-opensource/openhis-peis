package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售同期对比季度
 */
@Data
public class SellDateQuarterVo implements Serializable {
    private static final long serialVersionUID = -4842470101342978216L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @Excel(name = "用户名字")
    @ApiModelProperty(value = "用户名字")
    private String username;


    @ApiModelProperty(value = "第一季度目标额")
    private String target1;


    @ApiModelProperty(value = "第二季度目标额")
    private String target2;


    @ApiModelProperty(value = "第三季度目标额")
    private String target3;


    @ApiModelProperty(value = "第四季度目标额")
    private String target4;


    @ApiModelProperty(value = "备注")
    private String bz;




    @ApiModelProperty(value = "第一季度完成额")
    private Double complete1;




    @ApiModelProperty(value = "第二季度完成额")
    private Double complete2;




    @ApiModelProperty(value = "第三季度完成额")
    private Double complete3;




    @ApiModelProperty(value = "第四季度完成额")
    private Double complete4;


    @ApiModelProperty(value = "分中心")
    private String fzx;


    @ApiModelProperty(value = "工作年龄")
    private String workingAge;

    @ApiModelProperty(value = "第一季度目标额")
    private String endTarget1;


    @ApiModelProperty(value = "第二季度目标额")
    private String endTarget2;


    @ApiModelProperty(value = "第三季度目标额")
    private String endTarget3;


    @ApiModelProperty(value = "第四季度目标额")
    private String endTarget4;

    @Excel(name = "第一季度同比")
    @ApiModelProperty(value = "第一季度同比增长率")
    private String growth1;

    @Excel(name = "第一季度完成额")
    @ApiModelProperty(value = "第一季度完成额")
    private Double endComplete1;

    @Excel(name = "第二季度同比")
    @ApiModelProperty(value = "第二季度同比增长率")
    private String growth2;

    @Excel(name = "第二季度完成额")
    @ApiModelProperty(value = "第二季度完成额")
    private Double endComplete2;

    @Excel(name = "第三季度同比")
    @ApiModelProperty(value = "第三季度同比增长率")
    private String growth3;

    @Excel(name = "第三季度完成额")
    @ApiModelProperty(value = "第三季度完成额")
    private Double endComplete3;

    @Excel(name = "第四季度同比")
    @ApiModelProperty(value = "第四季度同比增长率")
    private String growth4;

    @Excel(name = "第四季度完成额")
    @ApiModelProperty(value = "第四季度完成额")
    private Double endComplete4;


}
