package com.center.medical.common.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 线上生成体检号参数
 */
@Data
public class OnlineGenerateParam implements Serializable {
    private static final long serialVersionUID = 903778739940521245L;

    @ApiModelProperty(value = "简码")
    private String jm;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "生成数量")
    private Integer count;


    public OnlineGenerateParam() {
    }

    public OnlineGenerateParam(String jm, String version, Integer count) {
        this.jm = jm;
        this.version = version;
        this.count = count;
    }

}
