package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 团检费用统计 分页参数
 */
@Data
public class PersonalTotalParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 633306361329911381L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户名")
    private String userName;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户名编号")
    private String userNo;


    @ApiModelProperty(value = "体检形式（海关用）")
    private String tjxs;

    @ApiModelProperty(value = "销售经理")
    private String name;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "不查询复查 1是0否")
    private Integer noReview;
}
