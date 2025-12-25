package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PictureDataVo implements Serializable {
    private static final long serialVersionUID = -3585324993327610921L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "科室名称")
    private String ksName;

    @ApiModelProperty(value = "收费项目id")
    private String idCharge;

    @ApiModelProperty(value = "状态")
    private String status;
}
