package com.center.medical.workflow.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 审批流材料审核 参数
 */
@Data
public class MaterialReviewParam implements Serializable {
    private static final long serialVersionUID = 843834396491781549L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "状态：-1.失效 0.等待开始 1.进行中 2.已通过 3.被驳回")
    private Integer status;


    public MaterialReviewParam(String id, Integer status) {
        this.id = id;
        this.status = status;
    }


    public MaterialReviewParam() {
    }
}
