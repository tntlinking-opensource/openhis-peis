package com.center.medical.sync.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成体检号参数
 */
@Data
public class GenerateCodeParam implements Serializable {
    private static final long serialVersionUID = 7930252696640794721L;

    @ApiModelProperty(value = "简码")
    private String jm;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "生成数量")
    private Integer count;


    public GenerateCodeParam() {
    }

    public GenerateCodeParam(String jm, String version, Integer count) {
        this.jm = jm;
        this.version = version;
        this.count = count;
    }
}
