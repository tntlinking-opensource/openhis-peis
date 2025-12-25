package com.center.medical.appadmin.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取手机号对应的最后体检日期
 */
@Data
public class GetLastTimeDto implements Serializable {
    private static final long serialVersionUID = -4954952825353759659L;

    @ApiModelProperty(value = "体检次数")
    private String number;

    @ApiModelProperty(value = "最后一次体检时间")
    private String lastTime;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

}
