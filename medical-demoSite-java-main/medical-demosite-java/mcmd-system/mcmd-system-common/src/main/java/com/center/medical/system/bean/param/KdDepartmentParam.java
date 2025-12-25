package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-05-20 16:03
 * @description: 金蝶中的部门信息查询参数
 */
@Data
public class KdDepartmentParam implements Serializable {

    private static final long serialVersionUID = 933384010911405844L;

    @ApiModelProperty(value = "部门名称")
    private String accountName;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
