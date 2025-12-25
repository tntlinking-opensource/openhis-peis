package com.center.medical.reservation.bean.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 新增团体预约记录实体类
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@ApiModel(value = "ReservationGroupParam", description = "新增团体预约记录实体类")
public class ReservationGroupParam implements Serializable {

    private static final long serialVersionUID = 766363371237553663L;

    @ApiModelProperty(value = "团体预约id")
    private String id;

    @ApiModelProperty(value = "分中心ID", required = true)
    @NotBlank(message = "请先选择预约的分中心!")
    private String branchId;

    @ApiModelProperty(value = "预约类型ID", required = true)
    @NotNull(message = "请先选择预约类型!")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称", required = true)
    private String levelName;

    @ApiModelProperty(value = "预约日期", required = true)
    @NotNull(message = "请先选择起始日期!")
    private Date startDate;

    @ApiModelProperty(value = "预约日期", required = true)
    @NotNull(message = "请先选择截至日期!")
    private Date endDate;

    @ApiModelProperty(value = "订单号", required = true)
    @NotBlank(message = "请先选择预约订单号!")
    private String orderNum;

    @ApiModelProperty(value = "订单名称", required = true)
    private String orderName;

    @ApiModelProperty(value = "套餐ID")
    private String comboId;

    @ApiModelProperty(value = "套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "销售经理ID", required = true)
    @NotBlank(message = "销售经理不能为空!")
    private String xsjlId;

    @ApiModelProperty(value = "销售经理", required = true)
    private String xsjlName;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "预约人数(上午)", required = true)
    @NotNull(message = "预约人数不能为空!")
    private Integer countAm;

    @ApiModelProperty(value = "预约人数(下午)")
    private Integer countPm;

    @ApiModelProperty(value = "备注", required = true)
    private String remark;
}
