package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页预约列表 返回数据
 */
@Data
public class QueryUnitDto implements Serializable {
    private static final long serialVersionUID = -4401555329139374164L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "公司")
    private String orgName;

}
