package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查看短信数据参数
 */
@Data
public class SmsDataParam implements Serializable {
    private static final long serialVersionUID = -2799969553572652467L;

    @ApiModelProperty(value = "通知类型， 详见数据表：md_short_message_type")
    private String notifyType;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "档案ID")
    private String archiveId;


}
