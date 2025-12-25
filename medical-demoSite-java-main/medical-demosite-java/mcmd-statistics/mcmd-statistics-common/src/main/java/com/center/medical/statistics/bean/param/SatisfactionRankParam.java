package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室满意度排名 分页参数
 */
@Data
public class SatisfactionRankParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5308450294686339126L;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "医生id")
    private String userid;

}
