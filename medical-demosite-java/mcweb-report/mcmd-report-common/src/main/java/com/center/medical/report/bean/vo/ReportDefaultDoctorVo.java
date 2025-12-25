package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告科室默认医生 分页返回数据
 */
@Data
public class ReportDefaultDoctorVo implements Serializable {
    private static final long serialVersionUID = 7656274533499303090L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @ApiModelProperty(value = "类型：0:健康 1:职业 2:健康+职业")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "人员类型：0:审核者 1:医师签名 2:审核者+医师签名")
    private Integer personnelType;
}
