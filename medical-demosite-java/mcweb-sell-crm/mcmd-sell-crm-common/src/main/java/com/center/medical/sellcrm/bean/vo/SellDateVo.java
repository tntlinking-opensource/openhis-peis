package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SellDateVo implements Serializable {


    private static final long serialVersionUID = -1529497568295345168L;


    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "销售")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "年同比")
    @ApiModelProperty(value = "同比增长率")
    private String growth;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @Excel(name = "开始目标额(元)")
    @ApiModelProperty(value = "开始全年目标")
    private Double ndmb;

    @Excel(name = "开始实际完成额(元)")
    @ApiModelProperty(value = "开始实际完成额")
    private Double complete;

    @Excel(name = "完成进度")
    @ApiModelProperty(value = "开始完成进度")
    private String completion1;

    @Excel(name = "结束目标额(元)")
    @ApiModelProperty(value = "结束全年目标")
    private Double ndmb2;

    @Excel(name = "结束实际完成额(元)")
    @ApiModelProperty(value = "结束实际完成额")
    private Double complete2;

    @Excel(name = "完成进度")
    @ApiModelProperty(value = "结束完成进度")
    private String completion2;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "工作年龄")
    private String workingAge;
}
