package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 一般检查审核保存参数
 */
@Data
public class DFGriddataDto implements Serializable {
    private static final long serialVersionUID = 1234851278456111165L;


    @ApiModelProperty(value = "体征词Id")
    private String tzcId;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "结论词名称")
    private String jlcName;

    @ApiModelProperty(value = "体征词")
    private String tzc;

    @ApiModelProperty(value = "检查项目")
    private String jcxm;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String examType;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "状态，removed删除，modified修改，added添加")
    private String state;
}
