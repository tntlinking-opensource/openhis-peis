package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售同期对比月
 */
@Data
public class SellDateMonthVo implements Serializable {
    private static final long serialVersionUID = 1230882133363601800L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

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


    @ApiModelProperty(value = "备注")
    private String bz;


    @ApiModelProperty(value = "工作年龄")
    private String workingAge;


    @ApiModelProperty(value = "target1")
    private Double target1;


    @ApiModelProperty(value = "1月实际完成额")
    private Double complete1;


    @ApiModelProperty(value = "target2")
    private Double target2;


    @ApiModelProperty(value = "2月实际完成额")
    private Double complete2;


    @ApiModelProperty(value = "target3")
    private Double target3;


    @ApiModelProperty(value = "3月实际完成额")
    private Double complete3;


    @ApiModelProperty(value = "target4")
    private Double target4;


    @ApiModelProperty(value = "4月实际完成额")
    private Double complete4;


    @ApiModelProperty(value = "target5")
    private Double target5;


    @ApiModelProperty(value = "5月实际完成额")
    private Double complete5;


    @ApiModelProperty(value = "target6")
    private Double target6;


    @ApiModelProperty(value = "6月实际完成额")
    private Double complete6;


    @ApiModelProperty(value = "target7")
    private Double target7;


    @ApiModelProperty(value = "7月实际完成额")
    private Double complete7;


    @ApiModelProperty(value = "target8")
    private Double target8;


    @ApiModelProperty(value = "8月实际完成额")
    private Double complete8;


    @ApiModelProperty(value = "target9")
    private Double target9;


    @ApiModelProperty(value = "9月实际完成额")
    private Double complete9;


    @ApiModelProperty(value = "target10")
    private Double target10;


    @ApiModelProperty(value = "10实际完成额")
    private Double complete10;




    @ApiModelProperty(value = "target11")
    private Double target11;


    @ApiModelProperty(value = "11月实际完成额")
    private Double complete11;


    @ApiModelProperty(value = "target12")
    private Double target12;


    @ApiModelProperty(value = "12实际完成额")
    private Double complete12;


    @ApiModelProperty(value = "target1")
    private Double endTarget1;

    @Excel(name = "1月同比")
    @ApiModelProperty(value = "一月同比增长率")
    private String growth1;

    @Excel(name = "1月实际完成额(元)")
    @ApiModelProperty(value = "1月实际完成额")
    private Double endComplete1;


    @ApiModelProperty(value = "target2")
    private Double endTarget2;

    @Excel(name = "2月同比")
    @ApiModelProperty(value = "二月同比增长率")
    private String growth2;

    @Excel(name = "2月实际完成额(元)")
    @ApiModelProperty(value = "2月实际完成额")
    private Double endComplete2;


    @ApiModelProperty(value = "target3")
    private Double endTarget3;

    @Excel(name = "3月同比")
    @ApiModelProperty(value = "三月同比增长率")
    private String growth3;

    @Excel(name = "3月实际完成额(元)")
    @ApiModelProperty(value = "3月实际完成额")
    private Double endComplete3;



    @ApiModelProperty(value = "target4")
    private Double endTarget4;

    @Excel(name = "4月同比")
    @ApiModelProperty(value = "四月同比增长率")
    private String growth4;


    @Excel(name = "4月实际完成额(元)")
    @ApiModelProperty(value = "4月实际完成额")
    private Double endComplete4;


    @ApiModelProperty(value = "target5")
    private Double endTarget5;

    @Excel(name = "5月同比")
    @ApiModelProperty(value = "五月同比增长率")
    private String growth5;

    @Excel(name = "5月实际完成额(元)")
    @ApiModelProperty(value = "5月实际完成额")
    private Double endComplete5;



    @ApiModelProperty(value = "target6")
    private Double endTarget6;

    @Excel(name = "6月同比")
    @ApiModelProperty(value = "六月同比增长率")
    private String growth6;

    @Excel(name = "6月实际完成额(元)")
    @ApiModelProperty(value = "6月实际完成额")
    private Double endComplete6;



    @ApiModelProperty(value = "target7")
    private Double endTarget7;

    @Excel(name = "7月同比")
    @ApiModelProperty(value = "七月同比增长率")
    private String growth7;


    @Excel(name = "7月实际完成额(元)")
    @ApiModelProperty(value = "7月实际完成额")
    private Double endComplete7;



    @ApiModelProperty(value = "target8")
    private Double endTarget8;

    @Excel(name = "8月同比")
    @ApiModelProperty(value = "八月同比增长率")
    private String growth8;

    @Excel(name = "8月实际完成额(元)")
    @ApiModelProperty(value = "8月实际完成额")
    private Double endComplete8;


    @ApiModelProperty(value = "target9")
    private Double endTarget9;

    @Excel(name = "9月同比")
    @ApiModelProperty(value = "九月同比增长率")
    private String growth9;

    @Excel(name = "9月实际完成额(元)")
    @ApiModelProperty(value = "9月实际完成额")
    private Double endComplete9;


    @ApiModelProperty(value = "target10")
    private Double endTarget10;

    @Excel(name = "10月同比")
    @ApiModelProperty(value = "十月同比增长率")
    private String growth10;


    @Excel(name = "10月实际完成额(元)")
    @ApiModelProperty(value = "10实际完成额")
    private Double endComplete10;

    @ApiModelProperty(value = "target11")
    private Double endTarget11;

    @Excel(name = "11月同比")
    @ApiModelProperty(value = "十一月同比增长率")
    private String growth11;

    @Excel(name = "11月实际完成额(元)")
    @ApiModelProperty(value = "11月实际完成额")
    private Double endComplete11;

    @ApiModelProperty(value = "target12")
    private Double endTarget12;

    @Excel(name = "12月实际完成额(元)")
    @ApiModelProperty(value = "十二月同比增长率")
    private String growth12;

    @ApiModelProperty(value = "12实际完成额")
    private Double endComplete12;




}
