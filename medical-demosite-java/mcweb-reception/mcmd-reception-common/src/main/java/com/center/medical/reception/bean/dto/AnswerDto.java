package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerDto implements Serializable {
    private static final long serialVersionUID = -1156981986696048049L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "价格")
    private String jg;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "所属科室")
    private String ssks;

    @ApiModelProperty(value = "科室id")
    private String idKs;

}
