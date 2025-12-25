package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导入名单-列表数据
 */
@Data
public class COListDataVo implements Serializable {
    private static final long serialVersionUID = 3182115126487737147L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "统收限额")
    private Double tsLimit;

    @ApiModelProperty(value = "部门")
    private String orgDepart;
}
