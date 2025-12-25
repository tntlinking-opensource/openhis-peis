package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-22 19:32
 * @description: 高危人员查询参数
 */
@Data
public class RiskclientParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5262278671626063175L;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "状态：0：未处理1：处理")
    private Integer tjzt;

    @ApiModelProperty(value = "销售经理（开单医生ID）")
    private String idOpendoctor;


}
