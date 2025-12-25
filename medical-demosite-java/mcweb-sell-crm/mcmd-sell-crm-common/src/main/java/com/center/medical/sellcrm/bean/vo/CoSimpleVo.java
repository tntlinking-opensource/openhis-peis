package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 最小套餐(Createcombo)实体类
 *
 * @author 路飞船长
 * @since 2022-11-15 09:31:06
 */
@Data
@ApiModel(value = "CoSimpleVo", description = "最小套餐实体类")
public class CoSimpleVo implements Serializable {

    private static final long serialVersionUID = 4583032176975351977L;

    @ApiModelProperty(value = "套餐ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;
}
