package com.center.medical.reservation.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 预约来检情况分析 返回数据
 */
@Data
public class ReAnalysisVo implements Serializable {
    private static final long serialVersionUID = -7443883431838040992L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "团体ID")
    private String intId;

    @Excel(name = "单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;


    @Excel(name = "vvip已约已检")
    @ApiModelProperty(value = "vvip已约已检")
    private String vvip1;

    @Excel(name = "vvip已约未检")
    @ApiModelProperty(value = "vvip已约未检")
    private String vvip2;

    @Excel(name = "vvip未约未检")
    @ApiModelProperty(value = "vvip未约未检")
    private String vvip3;

    @Excel(name = "vip已约已检")
    @ApiModelProperty(value = "vip已约已检")
    private String vip1;

    @Excel(name = "vip已约未检")
    @ApiModelProperty(value = "vip已约未检")
    private String vip2;

    @Excel(name = "vip未约未检")
    @ApiModelProperty(value = "vip未约未检")
    private String vip3;

    @Excel(name = "普通已约已检")
    @ApiModelProperty(value = "普通已约已检")
    private String ordinary1;

    @Excel(name = "普通已约未检")
    @ApiModelProperty(value = "普通已约未检")
    private String ordinary2;

    @Excel(name = "普通未约未检")
    @ApiModelProperty(value = "普通未约未检")
    private String ordinary3;
}
