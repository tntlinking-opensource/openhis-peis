package com.center.medical.report.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SendMsgFormDataParam implements Serializable {
    private static final long serialVersionUID = 3750679567310081830L;

    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "模板ID")
    private String messageName;

    @ApiModelProperty(value = "通知备注")
    private String notifyMemo;

    @ApiModelProperty(value = "是否立即发送,true是，false否")
    private String immediately;

    @ApiModelProperty(value = "发送时间")
    private String sendTime;



}
