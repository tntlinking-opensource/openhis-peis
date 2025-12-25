package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取医师返回数据
 */
@Data
public class GetDoctorVo implements Serializable {

    private static final long serialVersionUID = -7441690096988018457L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "用户名")
    private String username;

}
