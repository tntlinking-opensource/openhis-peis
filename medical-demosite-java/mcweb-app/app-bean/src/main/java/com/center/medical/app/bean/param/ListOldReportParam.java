package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询老系统报告完整参数
 */
@Data
public class ListOldReportParam implements Serializable {
    private static final long serialVersionUID = 7671961277231475517L;

    @ApiModelProperty(value = "加密数据")
    private String data;

    @ApiModelProperty(value = "授权码")
    private String authCode;


    public ListOldReportParam(String data, String authCode) {
        this.data = data;
        this.authCode = authCode;
    }

    public ListOldReportParam() {
    }
}
