package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (VCheckLisMain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:49
 */
@Data
@TableName("v_Check_LIS_Main")
@ApiModel(value = "VCheckLisMain", description = "$tableInfo.comment实体类")
public class VCheckLisMain extends Model<VCheckLisMain> implements Serializable {
    private static final long serialVersionUID = -71413806592512429L;

    @TableId(value = "CheckReqID")
    @ApiModelProperty(value = "检验流水号(申请单号)")
    private String checkreqid;

    @ApiModelProperty(value = "体检登记编号")
    private String checkregno;

    @ApiModelProperty(value = "申请日期")
    private Date checkregdate;

    @ApiModelProperty(value = "LIS 报告编号")
    private String reportno;

    @ApiModelProperty(value = "LIS 报告日期")
    private Date reprtdate;

    @ApiModelProperty(value = "LIS 报告医生工号")
    private String reportdoctorcode;

    @ApiModelProperty(value = "LIS 报告医生")
    private String reprtdoctorname;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "检验项目编号")
    private String itemcode;

    @ApiModelProperty(value = "检验项目名称")
    private String itemname;

    @ApiModelProperty(value = "申请科室")
    private String departname;

    @ApiModelProperty(value = "申请医生")
    private String doctorname;
}
