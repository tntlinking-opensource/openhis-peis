package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验样本-不合格样本添加数据
 */
@Data
public class VmFormdata implements Serializable {
    private static final long serialVersionUID = -38908982514896269L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "样本不合格原因ID")
    private String belowquestion;

    @ApiModelProperty(value = "备注")
    private String note;

}
