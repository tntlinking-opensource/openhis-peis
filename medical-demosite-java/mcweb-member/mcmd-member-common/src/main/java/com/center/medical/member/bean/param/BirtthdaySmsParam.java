package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-11-25 11:09
 * @description: 会员生日提醒回访数据
 */
@Data
public class BirtthdaySmsParam implements Serializable {
    private static final long serialVersionUID = -1974637219106183304L;

    @ApiModelProperty("选择的会员id集合，多个以英文逗号（,）隔开")
    private List<String> ids;

    @ApiModelProperty(value = "短消息名称ID")
    private String messageName;

    @ApiModelProperty(value = "短消息类型")
    private Integer messageType;

    @ApiModelProperty(value = "短消息正文")
    private String visitNote;

    @ApiModelProperty(value = "是否立即发送")
    private Boolean immediately;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
}
