package com.center.medical.statistics.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 批量职业健康检查复查通知书 返回数据
 */
@Data
public class GroupReviewNoticeVo implements Serializable {
    private static final long serialVersionUID = -6860010332827348426L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "pdf地址")
    private String url;

}
