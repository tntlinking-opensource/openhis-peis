package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddSatisficingVo implements Serializable {

    private static final long serialVersionUID = -5611965853000807161L;

    @Excel(name = "ID")
    @ApiModelProperty(value = "ID")
    private String sid;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别ID")
    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "团体部门")
    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "评价结果")
    @ApiModelProperty(value = "评价结果 1非常满意 2满意 3 基本满意 4 不满意 0不评价 无 未评价(整个分中心)//1非常满意 2满意4 不满意5取消不满意(科室)")
    private String appraiseResult;

    @Excel(name = "评价时间")
    @ApiModelProperty(value = "评价时间")
    private Date appraiseTime;

    @Excel(name = "评价备注")
    @ApiModelProperty(value = "评价备注")
    private String note;


}
