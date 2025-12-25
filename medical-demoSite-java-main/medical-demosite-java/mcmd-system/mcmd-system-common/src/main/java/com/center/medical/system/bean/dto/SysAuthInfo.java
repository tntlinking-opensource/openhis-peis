package com.center.medical.system.bean.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 系统授权信息
 * @date: 2023-02-24 9:52
 * @description:
 */
@Data
public class SysAuthInfo implements Serializable {

    private static final long serialVersionUID = 6015981679304024077L;

    @ApiModelProperty(value = "授权类型：0.永久授权 1.有期限收取")
    private Integer authType;

    @ApiModelProperty("到期时间")
    private Date expiredTime;

    @ApiModelProperty("开始使用日期")
    private Date startTime;

    @ApiModelProperty("机构")
    private String institutionName;

    @ApiModelProperty("机构ID")
    private String institutionId;

    @ApiModelProperty("授权码")
    private String authCode;

    @ApiModelProperty("授权版本")
    private String version;

    @ApiModelProperty("是否过期：true已过期，false未过期")
    private Boolean expired;

    @ApiModelProperty("距离到期还剩天数")
    private Long expiredAfterDay;

    @ApiModelProperty("过期提示内容")
    private String expiredTip;

    @ApiModelProperty("到期是否可用：0.不可用 1.可用")
    private Integer canUse;

    @ApiModelProperty("是否需要弹窗提示：0.不需要 1.需要")
    private Boolean isPop;
}
