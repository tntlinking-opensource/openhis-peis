package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送登记项目列表
 */
@Data
public class OSItemDataVo implements Serializable {
    private static final long serialVersionUID = 2396277542845268350L;


    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "科室名称")
    private String ksName;

    @ApiModelProperty(value = "样本类型")
    private String yblx;

    @ApiModelProperty(value = "外送机构id")
    private String wsjgId;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;
}
