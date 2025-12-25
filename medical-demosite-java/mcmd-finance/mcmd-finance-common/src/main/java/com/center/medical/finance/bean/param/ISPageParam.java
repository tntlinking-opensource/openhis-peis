package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 个检销售统计 分页 参数
 */
@Data
public class ISPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -1117760360569448618L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "类型 0客服1销售")
    private Integer type;

    @ApiModelProperty(value = "科室id")
    private String ksid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "hasks 标识")
    private Integer hasks;

    @ApiModelProperty(value = "按天查询,1是,0否")
    private Integer isByDay;

    @ApiModelProperty(value = "疫苗收费,1是,0否")
    private Integer isContainVaccine;

    @ApiModelProperty(value = "核酸,1是,0否")
    private Integer isContainNuclein;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "检查项目ids")
    private List<String> itemIds;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "疫苗科室")
    private String ymks;

}
