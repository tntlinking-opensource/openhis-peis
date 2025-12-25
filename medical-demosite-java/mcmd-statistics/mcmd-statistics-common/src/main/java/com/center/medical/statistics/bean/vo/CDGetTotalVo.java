package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目分布情况 获取合计数据
 */
@Data
public class CDGetTotalVo implements Serializable {
    private static final long serialVersionUID = -3474113998825103595L;

    @Excel(name = "体检者数量")
    @ApiModelProperty(value = "体检者数量")
    private String charge2;

    @Excel(name = "实收金额")
    @ApiModelProperty(value = "实收金额")
    private String charge3;

    @Excel(name = "应收金额")
    @ApiModelProperty(value = "应收金额")
    private String charge4;

    @Excel(name = "成本合计")
    @ApiModelProperty(value = "成本合计")
    private String charge5;
}
