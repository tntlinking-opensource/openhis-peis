package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
//个检危险值回访页面回访时的展示页
@Data
public class PeispatientEditVo implements Serializable {

    private static final long serialVersionUID = 474569364921374006L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "体检时间")
    private Date dateregister;

    @ApiModelProperty(value = "阳性检查项目")
    private String gwxm;

    @ApiModelProperty(value = "阳性结果")
    private String wjzxj;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "回访备注")
    private String memo;


}
