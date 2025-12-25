package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  团检费用统计 查询右边列表 参数
 */
@Data
public class PTTotalListParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2167463284700922073L;

    @ApiModelProperty(value = "体检形式：0.内检 1.外检 2.内检加外检")
    private Integer tjxs;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型")
    private Integer type;

}
