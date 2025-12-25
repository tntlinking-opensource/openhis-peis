package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class GetXsAndDataVo implements Serializable {

    private static final long serialVersionUID = 3892063516600500230L;


    @ApiModelProperty(value = "第一季度实际完成额")
    private String dyjdsjwce;

    @ApiModelProperty(value = "第二季度实际完成额")
    private String dejdsjwce;

    @ApiModelProperty(value = "第三季度实际完成额")
    private String dsjdsjwce;

    @ApiModelProperty(value = "第一季度实际完成额")
    private String dijdsjwce;

    @ApiModelProperty(value = "年度总完成额")
    private String ndzwce;

}
