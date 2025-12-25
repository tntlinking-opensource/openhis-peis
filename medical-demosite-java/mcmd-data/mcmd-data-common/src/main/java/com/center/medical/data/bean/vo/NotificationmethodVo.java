package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通知方式（领取方式）获取下拉数据
 */
@Data
public class NotificationmethodVo implements Serializable {
    private static final long serialVersionUID = 7284119955216249046L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "通知方式")
    private String methodName;
}
