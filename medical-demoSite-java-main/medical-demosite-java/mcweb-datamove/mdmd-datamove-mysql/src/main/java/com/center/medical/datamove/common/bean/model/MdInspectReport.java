package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 检验报告生成记录(MdInspectReport)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Data
@TableName("md_inspect_report")
@ApiModel(value = "MdInspectReport", description = "检验报告生成记录实体类")
public class MdInspectReport extends Model<MdInspectReport> implements Serializable {
    private static final long serialVersionUID = 502635742739569664L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "最近打印时间")
    private Date printTime;

    @ApiModelProperty(value = "打印次数")
    private Integer printCount;
}
