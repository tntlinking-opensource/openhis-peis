package com.center.medical.bean.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 新增分中心心时，添加其他跟分中心的关联表
 */
@Data
@AllArgsConstructor
public class AddBranchEvent {

    @ApiModelProperty(value = "分中心id")
    private String cid;


}
