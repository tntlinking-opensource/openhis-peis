package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 咨询与随访统计分页返回数据
 */
@Data
public class StatisticsListVo implements Serializable {
    private static final long serialVersionUID = -5334852807444143301L;

    @ApiModelProperty(value = "医生用户名")
    private String doctorUsername;

    @ApiModelProperty(value = "咨询类型，1.现场咨询 2.来电咨询 3.电话回访")
    private Integer consultType;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
