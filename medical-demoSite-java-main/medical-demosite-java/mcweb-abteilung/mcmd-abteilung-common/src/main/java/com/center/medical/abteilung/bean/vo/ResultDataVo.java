package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查看档案-右侧返回数据
 */
@Data
public class ResultDataVo implements Serializable {
    private static final long serialVersionUID = 7724881263639246021L;

    @ApiModelProperty(value = "科室")
    private String deptName;

    @ApiModelProperty(value = "医师")
    private String userName;

    @ApiModelProperty(value = "小结")
    private String conclusions;
}
