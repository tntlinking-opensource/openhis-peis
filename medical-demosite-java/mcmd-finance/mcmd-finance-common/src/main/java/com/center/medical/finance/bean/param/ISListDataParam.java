package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 个检销售统计 获取关联的数据 参数
 */
@Data
public class ISListDataParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 79227706316858628L;

    @ApiModelProperty(value = "疫苗收费,1是,0否")
    private Integer isContainVaccine;

    @ApiModelProperty(value = "核酸,1是,0否")
    private Integer isContainNuclein;


    @ApiModelProperty(value = "检查类型：0:健康检查类型 1:职业检查类型 2:健康+职业(职业)")
    private Integer type;

    @ApiModelProperty(value = "id")
    private String id;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "检查项目ids")
    private List<String> itemIds;
}
