package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导入体检者数据
 */
@Data
public class OfflineImportPeiParam implements Serializable {
    private static final long serialVersionUID = 28951980487431772L;

    @ApiModelProperty(value = "订单号集合")
    private List<String> ddhs;


    @ApiModelProperty(value = "订单号集合")
    private String fzxId;
}
