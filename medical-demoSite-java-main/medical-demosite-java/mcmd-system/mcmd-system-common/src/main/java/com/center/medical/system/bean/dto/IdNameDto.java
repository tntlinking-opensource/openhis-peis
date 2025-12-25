package com.center.medical.system.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回对象的id和名称
 */
@Data
public class IdNameDto implements Serializable {
    private static final long serialVersionUID = -1153478374553175799L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "name")
    private String name;


    public IdNameDto() {
    }

    public IdNameDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
