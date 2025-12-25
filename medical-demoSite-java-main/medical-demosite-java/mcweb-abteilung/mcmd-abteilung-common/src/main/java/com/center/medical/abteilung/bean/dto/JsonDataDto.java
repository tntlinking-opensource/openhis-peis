package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小结参数
 */
@Data
public class JsonDataDto implements Serializable {
    private static final long serialVersionUID = -9044164331330615659L;

    @ApiModelProperty(value = "是否弃检")
    private String sfqj;

    @ApiModelProperty(value = "危急值")
    private Integer wjz;

    @ApiModelProperty(value = "危急值级别")
    private String wjzjb;

    @ApiModelProperty(value = "收费项目id")
    private String sfxmId;

    @ApiModelProperty(value = "检查项目id")
    private String jcxmId;

    @ApiModelProperty(value = "体征词Id")
    private String tzcId;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;

    @ApiModelProperty(value = "检查描述")
    private String jcms;

    @ApiModelProperty(value = "inputResult")
    private String inputResult;



}
