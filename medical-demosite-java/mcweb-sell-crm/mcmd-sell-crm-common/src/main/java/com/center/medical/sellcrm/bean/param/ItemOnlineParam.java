package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 线上备单参数
 */
@Data
public class ItemOnlineParam implements Serializable {
    private static final long serialVersionUID = 4705895003587992014L;

    @ApiModelProperty(value = "订单号", position = 2)
    private String ddh;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "销售经理Id")
    private String xsjlid;

    @ApiModelProperty(value = "开单助理用户名")
    private String kdzlName;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "是否线上备单：null或其他.未线上备单 1.已线上备单")
    private Integer isOnline;

    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "按订单排序，1是")
    private Integer sortByOrder;

}
