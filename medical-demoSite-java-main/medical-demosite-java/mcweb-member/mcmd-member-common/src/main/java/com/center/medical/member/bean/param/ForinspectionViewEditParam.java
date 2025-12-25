package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ForinspectionViewEditParam implements Serializable {

    private static final long serialVersionUID = -4761109873351416148L;

    @ApiModelProperty(value = "ids")
    private String ids;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "公司")
    private String orgName;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "电话")
    private String phone;

    //0.弃检，1.迟捡来检，2.补检，3.看备注，4.已电话通知，5.补检来检，6.再通知
    @ApiModelProperty(value = "处理结果")
    private String sflj;

    @ApiModelProperty(value = "预约来检时间")
    private Date ljsj;

    @ApiModelProperty(value = "处理时间")
    private Date visitTime;

    @ApiModelProperty(value = "预处理结果")
    private String preResult;

    @ApiModelProperty(value = "预处理时间")
    private Date preTime;

    @ApiModelProperty(value = "项目名称")
    private String idExamfeeitem;

    @ApiModelProperty(value = "处理备注")
    private String memo;


}
