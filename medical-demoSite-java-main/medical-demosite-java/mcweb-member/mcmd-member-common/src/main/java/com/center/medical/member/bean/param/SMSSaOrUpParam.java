package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 预约短信回访保存参数
 */
@Data
public class SMSSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -5058571833278162111L;

    @ApiModelProperty(value = "体检号")
    private List<String> patientcode;

    @ApiModelProperty(value = "模板ID")
    private String idTemplate;

    @ApiModelProperty(value = "模板名称")
    private String modelName;

    @ApiModelProperty(value = "是否立即发送")
    private String immediately;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

}
