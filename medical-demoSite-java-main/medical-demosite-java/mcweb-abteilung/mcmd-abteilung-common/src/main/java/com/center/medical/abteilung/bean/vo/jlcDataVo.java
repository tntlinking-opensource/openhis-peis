package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结伦词列表数据
 */
@Data
public class jlcDataVo implements Serializable {

    private static final long serialVersionUID = 8801030554341113823L;

    @ApiModelProperty(value = "检查项目")
    private String jcxm;

    @ApiModelProperty(value = "体征词")
    private String tzc;

}
