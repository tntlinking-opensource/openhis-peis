package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目分布情况 分页参数
 */
@Data
public class ChargeDistributeParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3649819460877173736L;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "名称")
    private String name;
}
