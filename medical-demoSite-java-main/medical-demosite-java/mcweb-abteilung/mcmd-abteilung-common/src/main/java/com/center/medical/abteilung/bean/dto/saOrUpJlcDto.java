package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结论词保存(图像检查)
 */
@Data
public class saOrUpJlcDto implements Serializable {
    private static final long serialVersionUID = -6580247797835570050L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "体征词Id")
    private String tzcId;

    @ApiModelProperty(value = "检查项目id")
    private String jcxmId;

    @ApiModelProperty(value = "收费项目id")
    private String sfxmId;

    @ApiModelProperty(value = "检查描述")
    private String ms;

    @ApiModelProperty(value = "_id")
    private String _id;
}
