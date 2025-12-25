package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业必查拒检项目
 */
@Data
public class JjDataDto implements Serializable {
    private static final long serialVersionUID = -4248265880077272074L;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "收费项目名称:必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;

    @ApiModelProperty(value = "据检人用户名")
    private String jjr;

    @ApiModelProperty(value = "据检人签名路径")
    private String jjrqm;

    @ApiModelProperty(value = "据检时间")
    private Date jjsj;
}
