package com.center.medical.member.bean.vo;

import com.center.medical.bean.model.SmsRecord;
import com.center.medical.data.bean.model.Shortmessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 群发短信发送编辑
 */
@Data
public class smssendsVo implements Serializable {
    private static final long serialVersionUID = 9156145421350543545L;

    @ApiModelProperty(value = "短信发送记录")
    private SmsRecord smsRecord;

    @ApiModelProperty(value = "短信信息表")
    private Shortmessage shortmessage;
}
