package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取数据异常的参数
 */
@Data
public class DataExceptionPeiParam implements Serializable {
    private static final long serialVersionUID = 8288648160299504148L;

    @ApiModelProperty(value = "开单医生ID(不用传)")
    private String idOpendoctor;
}
