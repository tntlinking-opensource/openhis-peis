package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检团体分组保存上方基本数据
 */
@Data
public class GpFormdataDto implements Serializable {
    private static final long serialVersionUID = 7025439757165639015L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "订单号")
    private String ordernum;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "体检团体类型")
    private String idOrgclass;

    @ApiModelProperty(value = "销售员ID")
    private String idSalesperson;

    @ApiModelProperty(value = "销售人员")
    private String saleName;

    @ApiModelProperty(value = "任务已完成")
    private Integer fFinished;

    @ApiModelProperty(value = "团体任务预定时间")
    private Date datereservation;

    @ApiModelProperty(value = "计划结束日期")
    private Date planenddate;

    @ApiModelProperty(value = "体检者数量")
    private Integer countexaminee;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;
}
