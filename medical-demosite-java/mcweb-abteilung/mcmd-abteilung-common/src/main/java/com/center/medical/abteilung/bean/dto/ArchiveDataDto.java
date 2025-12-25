package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取上次在本科室体检的小结
 */
@Data
public class ArchiveDataDto implements Serializable {

    private static final long serialVersionUID = 8421705884238336025L;


    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "id")
    private String id;
}
