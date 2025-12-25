package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 上传名单-保存
 */
@Data
public class SaveUploadParam implements Serializable {
    private static final long serialVersionUID = 3086120980869407302L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "地址")
    private String path;

    @ApiModelProperty(value = "是否替换原有字符串,传ok直接覆盖,传其他 在后面拼接地址")
    private String fchange;
}
