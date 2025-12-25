package com.center.medical.datascreen.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 加项数据统计
 * @author xhp
 * @since 2023-06-14 9:32
 */
@Data
public class BranchDataSelectAddItemParam extends DatascreenBaseTimeAndBranchParam{
    @ApiModelProperty(value = "对比年份，最多三个",required = true)
    @NotNull
    @Size(max=3)
    private List<Integer> years;
}
