package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目分布情况 分页返回数据
 */
@Data
public class ChargeDistributeVo implements Serializable {
    private static final long serialVersionUID = -4936663508874773396L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "科室")
    @ApiModelProperty(value = "charge0")
    private String charge0;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "charge1")
    private String charge1;

    @Excel(name = "体检者数量")
    @ApiModelProperty(value = "charge2")
    private String charge2;

    @Excel(name = "实收金额")
    @ApiModelProperty(value = "charge3")
    private String charge3;

    @Excel(name = "应收金额")
    @ApiModelProperty(value = "charge4")
    private String charge4;

    @Excel(name = "成本合计")
    @ApiModelProperty(value = "charge5")
    private String charge5;

    @Excel(name = "折扣率")
    @ApiModelProperty(value = "charge6")
    private String charge6;

    @Excel(name = "个检数量")
    @ApiModelProperty(value = "charge7")
    private String charge7;

    @Excel(name = "团体自费数量")
    @ApiModelProperty(value = "charge8")
    private Integer charge8;

    @Excel(name = "团体统收数量")
    @ApiModelProperty(value = "charge9")
    private String charge9;

    @ApiModelProperty(value = "charge10")
    private String charge10;
}
