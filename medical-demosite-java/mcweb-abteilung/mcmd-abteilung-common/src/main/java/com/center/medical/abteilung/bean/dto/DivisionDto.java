package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询科室列表数据
 */
@Data
public class DivisionDto implements Serializable {
    private static final long serialVersionUID = -946968549837915575L;

    @ApiModelProperty(value = "科室编码，对应原系统的deptId")
    private String id;

    @ApiModelProperty(value = "科室名称")
    private String name;

    @ApiModelProperty(value = "数据报告格式，详见：com.center.medical.bean.enums.SjbggsType")
    private Integer sjbggs;

    @ApiModelProperty(value = "图片地址")
    private String imgpath;

    @ApiModelProperty(value = "接口类型，详见：com.center.medical.bean.enums.LpsJklxType")
    private String jklx;

    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @ApiModelProperty(value = " 英文名")
    private String englishName;

    @ApiModelProperty(value = " 课室号")
    private String ksh;


}
