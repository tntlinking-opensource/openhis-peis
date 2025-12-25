package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class GetNameDataVo implements Serializable {

    private static final long serialVersionUID = 5818097757989305390L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "科室名称")
    private String depId;
}
