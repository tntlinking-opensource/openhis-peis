package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取记账单位
 */
@Data
public class JzOrgVo implements Serializable {
    private static final long serialVersionUID = -441517724574592683L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "记账单位")
    private String jzdw;
}
