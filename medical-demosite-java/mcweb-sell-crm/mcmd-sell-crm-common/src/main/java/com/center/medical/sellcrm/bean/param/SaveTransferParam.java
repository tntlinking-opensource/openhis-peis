package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 客户转移-转移参数
 */
@Data
public class SaveTransferParam implements Serializable {
    private static final long serialVersionUID = 1186524508315382199L;

    @ApiModelProperty(value = "客户id集合")
    private List<String> ids;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "0未离职，1离职")
    private String key;
}
