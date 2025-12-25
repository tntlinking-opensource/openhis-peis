package com.center.medical.machine.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 读卡参数
 */
@Data
public class ReadCardParam implements Serializable {
    private static final long serialVersionUID = -6920673114484463328L;

    @ApiModelProperty(value = "来自客户端的消息,比如addItem科室加项,report报告打印,queue排队调整,register自助登记等")
    private String message;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像图片")
    private String img;
}
