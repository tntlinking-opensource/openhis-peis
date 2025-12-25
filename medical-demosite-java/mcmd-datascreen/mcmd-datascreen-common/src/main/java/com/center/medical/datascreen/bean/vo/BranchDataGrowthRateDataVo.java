package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对比年份对应数组数据
 * @author xhp
 * @since 2023-06-15 9:34
 */
@Data
public class BranchDataGrowthRateDataVo {
    @ApiModelProperty("年份")
    private int year;
    @ApiModelProperty("增长率数组")
    private String[] value;
}
