package com.center.medical.machine.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.naming.directory.SearchResult;
import java.io.Serializable;

/**
 * 自助登记
 */
@Data
public class RegisteredEntity implements Serializable {
    private static final long serialVersionUID = -8580019520091930793L;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private String idSex;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "照片")
    private String picture;

    @ApiModelProperty(value = "操作")
    private String op;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "是否支付")
    private Integer isPay;

    @ApiModelProperty(value = "是否选择")
    private Integer isSelected;

    @ApiModelProperty(value = "项目id,多个以英文逗号分割")
    private String itemIds;

    @ApiModelProperty(value = "分组id")
    private String groupIds;

    @ApiModelProperty(value = "包id(普通套餐id)")
    private String packageId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "线上")
    private Integer online;

    @ApiModelProperty(value = "是否注册")
    private Integer canReg;

    @ApiModelProperty(value = "paid")
    private String paid;
}
