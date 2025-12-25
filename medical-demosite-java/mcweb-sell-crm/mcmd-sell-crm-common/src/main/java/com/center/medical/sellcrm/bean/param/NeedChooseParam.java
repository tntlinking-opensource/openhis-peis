package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 是否需要选择
 */
@Data
public class NeedChooseParam implements Serializable {
    private static final long serialVersionUID = -4244395622383112261L;

    @NotNull(message = "id不能为空!")
    @ApiModelProperty(value = "id")
    private String id;

    @NotNull(message = "操作方式不能为空!")
    @ApiModelProperty(value = "操作方式 0提交、1变更")
    private Integer type;


    public NeedChooseParam(String id, Integer type) {
        this.id = id;
        this.type = type;
    }


    public NeedChooseParam() {
    }
}
