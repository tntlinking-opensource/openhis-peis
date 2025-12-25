package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目列表数据
 */
@Data
public class GetItemDataVo implements Serializable {

    private static final long serialVersionUID = 3657718658106600703L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "科室名称")
    private String departName;

    @ApiModelProperty(value = "样本类型")
    private String yblx;

    @ApiModelProperty(value = "试管颜色")
    private String bz;


}
