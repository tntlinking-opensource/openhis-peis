package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询基础数据收费项目参数
 */
@Data
public class SfxmDataParam implements Serializable {
    private static final long serialVersionUID = -8194735587451347091L;

    @ApiModelProperty(value = "体检类型数值")
    private String tjValue;

    @ApiModelProperty(value = "适用性别数值--用于调取不同类别的收费项目")
    private String syxbValue;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;
    
    @ApiModelProperty(value = "开始价格区间")
    private Double price_from;

    @ApiModelProperty(value = "结束价格区间")
    private Double price_to;

}
