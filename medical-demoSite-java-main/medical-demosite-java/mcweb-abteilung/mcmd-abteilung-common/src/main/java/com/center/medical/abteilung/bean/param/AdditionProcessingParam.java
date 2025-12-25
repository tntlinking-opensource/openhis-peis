package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 加项处理
 */
@Data
public class AdditionProcessingParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -7860533602669966538L;

    @ApiModelProperty(value = "数据类型 0:检验科 1：其他")
    private Integer type;

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

}
