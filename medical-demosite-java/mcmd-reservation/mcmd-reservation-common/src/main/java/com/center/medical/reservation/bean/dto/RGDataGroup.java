package com.center.medical.reservation.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队预约信息——团体信息
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@ApiModel(value = "RGDataGroup", description = "团队预约信息——团体信息")
public class RGDataGroup {

    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "订单名称")
    @Excel(name = "订单名称")
    private String orderName;

    @ApiModelProperty(value = "销售经理")
    @Excel(name = "销售经理")
    private String xsjlName;

    @ApiModelProperty(value = "预约人数")
    @Excel(name = "预约人数")
    private Integer countTotal;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;
}
