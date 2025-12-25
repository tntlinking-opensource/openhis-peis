package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康团检样本保存表格数据
 */
@Data
public class GHGridData implements Serializable {
    private static final long serialVersionUID = 2834619736643732767L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "状态 removed删除，modified修改，added添加")
    private String state;

    @ApiModelProperty(value = "组id")
    private String groupId;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "团检报告ID")
    private String ballCheckId;

    @ApiModelProperty(value = "已开始体检：0或null.否 1.是")
    private Integer fExamstarted;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

}
