package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取数据异常体检者数据 返回数据
 */
@Data
public class DataExceptionPeiVo implements Serializable {
    private static final long serialVersionUID = 446388145424523616L;

    @ApiModelProperty(value = "体检者id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "套餐id")
    private String idTjtc;

    @ApiModelProperty(value = "体检者收费项目的数量")
    private String pfCount;

    @ApiModelProperty(value = "套餐项目的数量")
    private String itemsCount;
}
