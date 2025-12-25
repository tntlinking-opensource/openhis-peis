package com.center.medical.report.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告中科室默认医生(ReportDefaultDoctor)表实体类
 *
 * @author ay
 * @since 2024-08-21 16:54:18
 */
@Data
@TableName("md_report_default_doctor")
@ApiModel(value = "ReportDefaultDoctor", description = "报告中科室默认医生实体类")
public class ReportDefaultDoctor extends Model<ReportDefaultDoctor> implements Serializable {
    private static final long serialVersionUID = -54338974660150411L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "科室ID")
    private String depId;


    @ApiModelProperty(value = "用户编号")
    private String userId;


    @ApiModelProperty(value = "类型：0:健康 1:职业 2:健康+职业")
    private Integer type;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
    
    @ApiModelProperty(value = "人员类型：0:审核者 1:医师签名 2:审核者+医师签名")
    private Integer personnelType;

    @TableField(exist = false)
    @ApiModelProperty(value = "科室名称")
    private String deptName;


    @TableField(exist = false)
    @ApiModelProperty(value = "用户名称")
    private String userName;

}
