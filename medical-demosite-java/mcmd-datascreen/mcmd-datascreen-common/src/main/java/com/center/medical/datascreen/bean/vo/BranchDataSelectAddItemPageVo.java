package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 加项数据统计详情
 * @author xhp
 * @since 2023-06-17 8:35
 */
@Data
public class BranchDataSelectAddItemPageVo {
    @ApiModelProperty("统计日期（从） yyyy-MM-dd")
    private String startDate;
    @ApiModelProperty("统计日期（至） yyyy-MM-dd")
    private String endDate;
    @ApiModelProperty("加项医生")
    private String userName;
    @ApiModelProperty("加项总价格")
    private String totalPrice;
    @ApiModelProperty("加项总优惠")
    private String totalFactprice;
}
