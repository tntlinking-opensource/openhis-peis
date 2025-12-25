package com.center.medical.sellcrm.bean.dto;

import com.center.medical.bean.dto.SysBranchDto;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞船长
 * @date: 2022/12/16 17:02
 * @description: 订单信息
 */
@Data
@ApiModel(value = "CreateorderDto", description = "订单信息")
public class CreateorderDto implements Serializable {
    private static final long serialVersionUID = 367238939628547064L;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "订单名称")
    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "团体ID")
    private String khdwmcid;

    @ApiModelProperty(value = "分中心IDs")
    private String fzxid;

    @ApiModelProperty(value = "分中心列表")
    private List<SysBranchDto> branchList;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "团检今天已预约vip及vvip的人数")
    private String reservationCount;

}
