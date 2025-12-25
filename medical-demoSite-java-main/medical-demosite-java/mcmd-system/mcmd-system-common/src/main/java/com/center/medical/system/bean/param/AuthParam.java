package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 生成授权信息参数
 * @date: 2023-02-24 9:52
 * @description:
 */
@Data
public class AuthParam implements Serializable {
    private static final long serialVersionUID = 2523482652759181964L;

    @ApiModelProperty(value = "授权类型：0.永久授权 1.有期限收取")
    private Integer authType;

    @ApiModelProperty("到期时间")
    private Date expiredTime;

    @ApiModelProperty("开始使用日期")
    private Date startTime;

    @ApiModelProperty("合作机构ID")
    private String institutionId;

    @ApiModelProperty("授权版本")
    private String version;

    @ApiModelProperty("类型：0.第一次授权，1.续期")
    private String type;

    @ApiModelProperty("到期是否可用：0.不可用 1.可用")
    private Integer canUse;

    @ApiModelProperty(value = "授权备注")
    private String remark;

}
