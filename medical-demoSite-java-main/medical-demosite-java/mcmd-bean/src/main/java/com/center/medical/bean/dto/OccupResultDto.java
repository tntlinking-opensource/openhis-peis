package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/12/10 07:14
 * @description: 职业总检结果
 */
@Data
public class OccupResultDto implements Serializable {
    private static final long serialVersionUID = -5866097284310229537L;

    /**
     * 综述
     */
    @ApiModelProperty(value = "综述")
    private String summarize;

    /**
     * 健康建议
     */
    @ApiModelProperty(value = "健康建议")
    private String proposal;

    /**
     * 结论
     */
    @ApiModelProperty(value = "结论")
    private String verdict;

    /**
     * 阳性结果
     */
    @ApiModelProperty(value = "阳性结果")
    private String posistive;

    /**
     * 总检时间
     */
    @ApiModelProperty(value = "总检时间")
    private String examTime;

}
