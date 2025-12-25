package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 我要提醒-获取科室
 */
@Data
public class RemindKsVo implements Serializable {
    private static final long serialVersionUID = 2135672817848310784L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数据报告格式，详见：com.center.medical.bean.enums.SjbggsType")
    private String sjbggs;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @ApiModelProperty(value = "图片地址")
    private String imgpath;

    @ApiModelProperty(value = "接口类型，详见：com.center.medical.bean.enums.LpsJklxType")
    private String jklx;
}
