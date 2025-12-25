package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取检验科艾迪康列表
 */
@Data
public class AdiconGridDataVo implements Serializable {
    private static final long serialVersionUID = -8163792459767615677L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "examitemCode3")
    private String examitemCode3;

}
