package com.center.medical.datascreen.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 加项数据统计列表
 * @author xhp
 * @since 2023-06-17 8:58
 */
@Data
public class BranchDataSelectAddItemPageParam {
    @ApiModelProperty(value = "对比年份，最多三个",required = true)
    @NotNull
    @Size(max=3)
    private List<Integer> years;

    @ApiModelProperty(value = "分中心id",required = true)
    @NotEmpty
    private String branchId;
}
