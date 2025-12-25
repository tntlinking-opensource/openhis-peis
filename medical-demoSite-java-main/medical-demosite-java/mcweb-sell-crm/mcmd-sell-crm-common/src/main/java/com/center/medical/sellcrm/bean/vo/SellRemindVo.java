package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 备忘提醒分页查询返回参数
 */
@Data
public class SellRemindVo implements Serializable {
    private static final long serialVersionUID = 1576703869143084747L;


    @ApiModelProperty(value = "合同签订日期")
    private String id;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "提醒时间")
    private Date remindTime;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态 0未结束 1 已结束")
    private Integer status;

    @ApiModelProperty(value = "due")
    private String due;

}
