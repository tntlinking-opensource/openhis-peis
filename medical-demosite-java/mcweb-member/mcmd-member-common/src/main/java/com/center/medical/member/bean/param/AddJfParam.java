package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 积分充值保存参数
 */
@Data
public class AddJfParam implements Serializable {
    private static final long serialVersionUID = 2449474854662137039L;

    @ApiModelProperty(value = "formdata")
    private JFFormDataParam formdata;

    @ApiModelProperty(value = "griddata")
    private List<JFGridDataParam> griddata;

}
