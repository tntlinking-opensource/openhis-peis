package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送登记，保存表格数据
 */
@Data
public class SrGriddataDto implements Serializable {
    private static final long serialVersionUID = 2603940618226027589L;

    @ApiModelProperty(value = "外送机构id")
    private String wsjgId;

    @ApiModelProperty(value = "科室名称")
    private String ksName;

    @ApiModelProperty(value = "yblx")
    private String yblx;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "_id")
    private String _id;

    @ApiModelProperty(value = "_uid")
    private String _uid;
}
