package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class GetSignVo implements Serializable {
    private static final long serialVersionUID = -8668363220126549737L;

    @ApiModelProperty(value = "检查项目")
    private String jcxm;

    @ApiModelProperty(value = "体征词")
    private String tzc;

    @ApiModelProperty(value = "体征词Id")
    private String tzcId;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;

    @ApiModelProperty(value = "结论词名称")
    private String jlcName;

    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String examType;

}
