package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 危急值提报 获取科室数据
 */
@Data
public class GetKsVo implements Serializable {
    private static final long serialVersionUID = -2798978863692964252L;

    @ApiModelProperty(value = "科室名称")
    private String name;

    @ApiModelProperty(value = "科室号")
    private String id;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "小结")
    private String conclusions;

}
