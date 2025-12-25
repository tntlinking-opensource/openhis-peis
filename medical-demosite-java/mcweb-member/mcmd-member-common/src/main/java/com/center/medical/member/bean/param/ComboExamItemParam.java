package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 迟补检回访分页查询用到的方法的param
 */
@Data
public class ComboExamItemParam implements Serializable {

    private static final long serialVersionUID = 8745562462372811753L;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "收费项目Id")
    private String itemId;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;
}
