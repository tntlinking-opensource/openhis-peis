package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 快捷开药-保存表格数据
 */
@Data
public class PbGriddataDto implements Serializable {
    private static final long serialVersionUID = 1129071305803363439L;

    @ApiModelProperty(value = "状态 added添加，modified更新，removed删除")
    private String state;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "药品ID")
    private String drugId;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "开药时间")
    private Date prescribeTime;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "数量")
    private String prescribeNum;

    @ApiModelProperty(value = "备注")
    private String prescribeNote;



}
