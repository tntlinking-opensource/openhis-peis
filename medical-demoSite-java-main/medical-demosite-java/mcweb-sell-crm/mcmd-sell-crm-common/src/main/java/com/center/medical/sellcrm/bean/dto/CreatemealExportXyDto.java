package com.center.medical.sellcrm.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-26 15:30
 * @description: 导出协议套餐信息
 */
@Data
public class CreatemealExportXyDto implements Serializable {
    private static final long serialVersionUID = -2711670035207213269L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @Excel(name = "工种")
    @ApiModelProperty(value = "工种")
    private String workName;

    @ApiModelProperty(value = "接害因素id")
    private String jhys;

    @Excel(name = "职业病危害因素")
    @ApiModelProperty(value = "接害因素名称")
    private String jhysName;

    @ApiModelProperty(value = "职业体检类别，详见：com.center.medical.bean.enums.ZYTJLB")
    private String zytjlb;

    @ApiModelProperty(value = "num")
    private String num;

    @ApiModelProperty(value = "体检类型id")
    private Integer tjlx;

    @Excel(name = "体检类型")
    @ApiModelProperty(value = "体检类型名称")
    private String tjlxName;

    @Excel(name = "职业体检项目")
    @ApiModelProperty(value = "职业体检项目")
    private String zyItems;

    @Excel(name = "增加健康项目")
    @ApiModelProperty(value = "增加健康项目")
    private String jkItems;

    @ApiModelProperty(value = "id")
    private String id;
}
