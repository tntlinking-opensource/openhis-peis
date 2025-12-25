package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 加项数据统计
 * @author xhp
 * @since 2023-06-14 9:56
 */
@Data
public class BranchDataSelectAddItemVo {
    @ApiModelProperty("总人数")
    private int number;
    @ApiModelProperty("费用(元)")
    private double amount;
    @ApiModelProperty("同比增长率")
    private String growthRate;
    @ApiModelProperty("对比年份对应数组数据")
    private List<BranchDataGrowthRateDataVo> data;
    @ApiModelProperty(value = "横坐标日期",notes = "最近七天")
    private String[] days;
}
