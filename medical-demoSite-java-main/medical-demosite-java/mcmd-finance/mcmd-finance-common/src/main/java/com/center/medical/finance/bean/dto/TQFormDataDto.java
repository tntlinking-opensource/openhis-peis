package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 记账管理-记账结算保存 上方体检者数据
 */
@Data
public class TQFormDataDto implements Serializable {
    private static final long serialVersionUID = -4214199214616700995L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "记账金额")
    private Double jzje;

}
