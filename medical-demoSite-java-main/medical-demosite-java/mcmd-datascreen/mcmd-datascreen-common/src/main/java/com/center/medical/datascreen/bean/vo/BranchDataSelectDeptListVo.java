package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 科室工作量列表
 * @author xhp
 * @since 2023-06-20 9:28
 */
@Data
public class BranchDataSelectDeptListVo {
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "人数")
    private int number;

    @ApiModelProperty(value = "图片地址")
    private String imgpath;
}
