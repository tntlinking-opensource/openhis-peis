package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验样品-检验加项参数
 */
@Data
public class HandleNewParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -8952309664138845219L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "体检团体")
    private String khdwmcid;

    @ApiModelProperty(value = "处理状态")
    private String status;

    @ApiModelProperty(value = "类型(老系统searchData中的type)")
    private String lx;

    @ApiModelProperty(value = "数据类型 0:检验科 1：其他")
    private Integer type;
}
