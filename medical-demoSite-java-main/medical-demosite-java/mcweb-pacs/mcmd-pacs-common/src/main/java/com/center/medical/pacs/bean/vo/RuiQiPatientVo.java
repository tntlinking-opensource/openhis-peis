package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RuiQiPatientVo implements Serializable {
    private static final long serialVersionUID = 1590835306664031441L;

    @ApiModelProperty(value = "年龄")
    private Integer Age;

    @ApiModelProperty(value = "生日")
    private String Birthday;

    @ApiModelProperty(value = "性别，0男1女")
    private Integer Gender;

    @ApiModelProperty(value = "体检号")
    private String PatientID;

    @ApiModelProperty(value = "请求日期，默认为当天")
    private String RequestDate;

    @ApiModelProperty(value = "名称")
    private String Name;

}
