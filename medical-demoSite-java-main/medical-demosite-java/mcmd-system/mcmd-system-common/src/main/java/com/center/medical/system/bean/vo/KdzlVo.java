package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开单助手返回数据
 */
@Data
public class KdzlVo implements Serializable {
    private static final long serialVersionUID = 206129672606945706L;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "分中心")
    private String ssFzx;

    @ApiModelProperty(value = "科室名称")
    private String ssDep;
}
