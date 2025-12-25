package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团检客户没有增加项目自动保存收费信息
 */
@Data
public class autoSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -2228396079546364004L;

    @ApiModelProperty(value = "统费")
    private Double tongFei;

}
