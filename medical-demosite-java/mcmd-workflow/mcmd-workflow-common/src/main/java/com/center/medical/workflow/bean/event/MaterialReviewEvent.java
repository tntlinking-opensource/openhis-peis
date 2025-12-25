package com.center.medical.workflow.bean.event;

import com.center.medical.workflow.bean.param.MaterialReviewParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: ay
 * @date: 2024-04-08 20:38
 * @description: 审批流材料时间
 */
@Data
@AllArgsConstructor
public class MaterialReviewEvent {

    @ApiModelProperty(value = "参数")
    private MaterialReviewParam param;


}
