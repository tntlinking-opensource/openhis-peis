package com.center.medical.datascreen.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 活动产品销售(项目)
 * @author xhp
 * @since 2023-06-05 15:37
 */
@Data
public class PlatformDataSelectItemsListParam extends PlatformDataBaseParam{
    @ApiModelProperty(value = "项目分类id",required = true)
    @NotEmpty
    private List<String> ids;
}
