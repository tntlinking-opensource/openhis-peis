package com.center.medical.report.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class ReGriddata implements Serializable {
    private static final long serialVersionUID = 2678288958310577947L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;
}
