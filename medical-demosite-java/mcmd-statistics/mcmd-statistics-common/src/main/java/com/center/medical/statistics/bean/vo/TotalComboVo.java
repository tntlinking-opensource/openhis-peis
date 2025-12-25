package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每月每日套餐统计 返回数据
 */
@Data
public class TotalComboVo implements Serializable {
    private static final long serialVersionUID = -8958204888710638123L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="登记日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "combo0")
    private String combo0;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "combo1")
    private String combo1;

    @Excel(name = "数量")
    @ApiModelProperty(value = "combo2")
    private String combo2;

    @Excel(name = "套餐优惠价")
    @ApiModelProperty(value = "套餐优惠价")
    private String tcyhj;

    @Excel(name = "优惠统收合计")
    @ApiModelProperty(value = "优惠统收合计")
    private String yhtshj;

    @Excel(name = "数量(个人)")
    @ApiModelProperty(value = "combo5")
    private String combo5;

    @Excel(name = "数量(团体)")
    @ApiModelProperty(value = "combo6")
    private String combo6;


}
