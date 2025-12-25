package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-17 14:58
 * @description: 前台-登记-修改信息
 */
@Data
public class RegisterEditParam implements Serializable {
    private static final long serialVersionUID = 1885139431381912775L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;
}
