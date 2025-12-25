package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 查询复查通知单 返回数据
 */
@Data
public class createReviewVo implements Serializable {
    private static final long serialVersionUID = 6819185094787139000L;

    @ApiModelProperty(value = "职业病")
    private Map<String, Object> zyb;

    @ApiModelProperty(value = "禁忌症")
    private Map<String, Object> jjz;

    @ApiModelProperty(value = "复查")
    private Map<String, Object> fc;
}
