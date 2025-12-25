package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取领取人数据
 */
@Data
public class LqrDataVo implements Serializable {

    private static final long serialVersionUID = 4150977749412851106L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户名称")
    private String occupationSummary;

    @ApiModelProperty(value = "输入码")
    private String inCode;

    @ApiModelProperty(value = "部门名称")
    private String inputCode;
}
