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
 * (VCheckLisDetail)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:47
 */
@Data
@TableName("v_Check_LIS_Detail")
@ApiModel(value = "VCheckLisDetail", description = "$tableInfo.comment实体类")
public class VCheckLisDetail extends Model<VCheckLisDetail> implements Serializable {
    private static final long serialVersionUID = -47117177905701514L;

    @TableId(value = "CheckReqID")
    @ApiModelProperty(value = "检验流水号(申请单号)")
    private String checkreqid;

    @ApiModelProperty(value = "检验小项目编号")
    private String subitemcode;

    @ApiModelProperty(value = "检验小项目名称")
    private String subitemname;

    @ApiModelProperty(value = "结果")
    private String resultcode;

    @ApiModelProperty(value = "结果单位")
    private String resultunit;

    @ApiModelProperty(value = "结论（上下箭头代表偏高和偏低）")
    private String report;

    @ApiModelProperty(value = "参考范围")
    private String limitrange;
}
