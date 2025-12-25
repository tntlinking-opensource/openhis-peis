package com.center.medical.sellcrm.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导入销售年度目标 表格数据
 */
@Data
public class ImportYearTargetDto implements Serializable {
    private static final long serialVersionUID = -5121904847751024611L;

    @ApiModelProperty(value = "销售经理")
    @Excel(name = "销售经理")
    private String xsjlName;


    @ApiModelProperty(value = "分中心")
    @Excel(name = "分中心")
    private String fzxName;


    @ApiModelProperty(value = "目标额（元）")
    @Excel(name = "目标额（元）")
    private Double targetAmount;

}
