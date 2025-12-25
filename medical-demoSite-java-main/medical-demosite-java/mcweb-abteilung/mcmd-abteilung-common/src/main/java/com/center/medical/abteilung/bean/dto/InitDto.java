package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取科室初始化数据
 */
@Data
public class InitDto implements Serializable {
    private static final long serialVersionUID = 8056949429669986794L;

    @ApiModelProperty(value = "创建ip")
    private String create_url;

    @ApiModelProperty(value = "录入人ID")
    private String lrrId;

    @ApiModelProperty(value = "录入人名称")
    private String lrr;

    @ApiModelProperty(value = "录入时间")
    private String lrsj;

    public InitDto() {
    }

    public InitDto(String create_url, String lrrId, String lrr, String lrsj) {
        this.create_url = create_url;
        this.lrrId = lrrId;
        this.lrr = lrr;
        this.lrsj = lrsj;
    }
}
