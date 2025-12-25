package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存体检者信息
 */
@Data
public class COGridDataDto implements Serializable {
    private static final long serialVersionUID = -5908054177786740558L;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "统收限额")
    private Double tsLimit;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "id")
    private String id;
}
