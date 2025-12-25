package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 科室工作量
 * @author xhp
 * @since 2023-06-13 8:29
 */
@Data
public class BranchDataSelectDeptVo {
    @ApiModelProperty(value = "科室数量")
    private int count;
    @ApiModelProperty(value = "体检人次")
    private int number;
}
