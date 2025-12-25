package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 总检工作量 分页参数
 */
@Data
public class GeneralCheckingParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3996233051133478758L;


    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "是否汇总,0否，1是")
    private Integer sfhz;
}
