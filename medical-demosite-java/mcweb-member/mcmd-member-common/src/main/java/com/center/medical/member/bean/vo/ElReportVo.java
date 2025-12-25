package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员管理-档案合并返回数据
 */
@Data
public class ElReportVo implements Serializable {
    private static final long serialVersionUID = -718376142016854131L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "地区")
    private String resarea;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "体检者类型")
    private String patientclass;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;
}
