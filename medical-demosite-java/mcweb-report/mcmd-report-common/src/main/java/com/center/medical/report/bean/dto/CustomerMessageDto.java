package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询样本客户的一些信息
 */
@Data
public class CustomerMessageDto implements Serializable {
    private static final long serialVersionUID = 1085403433295741458L;

    @ApiModelProperty(value = "实际从业人数")
    private String sjcyrs;

    @ApiModelProperty(value = "主要产品")
    private String zycp;

    @ApiModelProperty(value = "工艺流程")
    private String gylc;

    @ApiModelProperty(value = "主要原辅料")
    private String zyyfl;

    @ApiModelProperty(value = "职业病危害因素")
    private String zybwhys;
}
