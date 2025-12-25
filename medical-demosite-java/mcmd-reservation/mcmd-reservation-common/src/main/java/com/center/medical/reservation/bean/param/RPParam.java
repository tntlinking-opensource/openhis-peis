package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-21 14:46
 * @description: 预约详情-个人预约记录查询参数
 */
@Data
@ApiModel(value = "预约详情-个人预约记录查询参数")
public class RPParam implements Serializable {
    private static final long serialVersionUID = -7647824138608864661L;
    
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "客户类型")
    private Integer levelId;
}
