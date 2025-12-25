package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登记列表发送到短信
 */
@Data
public class SaveOrUpdateMsgParam implements Serializable {
    private static final long serialVersionUID = -4230625359347817887L;

    @ApiModelProperty("体检号集合，多个以英文逗号（,）隔开")
    private List<String> patientcodes;

    @ApiModelProperty(value = "模板id")
    private String idTemplate;

    @ApiModelProperty(value = "短消息名称")
    private String modelName;

    @ApiModelProperty(value = "是否立即发送，true或false，注意是字符串哈")
    private String immediately;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;


}
