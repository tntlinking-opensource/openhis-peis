package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平安好医生 到检确认发送url参数
 */
@Data
public class ConfirmOrderUrlParam implements Serializable {
    private static final long serialVersionUID = 1639253554663996404L;

    @ApiModelProperty(value = "data加密数据")
    private String data;


    @ApiModelProperty(value = "关联表数据")
    private String countreportoccupation;


    public ConfirmOrderUrlParam(String data, String countreportoccupation) {
        this.data = data;
        this.countreportoccupation = countreportoccupation;
    }


    public ConfirmOrderUrlParam() {
    }
}
