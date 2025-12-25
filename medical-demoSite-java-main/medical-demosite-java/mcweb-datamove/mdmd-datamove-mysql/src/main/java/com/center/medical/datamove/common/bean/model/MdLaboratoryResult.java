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
 * KS检验科接收数据(MdLaboratoryResult)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Data
@TableName("md_laboratory_result")
@ApiModel(value = "MdLaboratoryResult", description = "KS检验科接收数据实体类")
public class MdLaboratoryResult extends Model<MdLaboratoryResult> implements Serializable {
    private static final long serialVersionUID = -68682154285810561L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "项目ID")
    private String projectsId;

    @ApiModelProperty(value = "LIS代码")
    private String lisCode;

    @ApiModelProperty(value = "检验项目")
    private Integer projects;

    @ApiModelProperty(value = "重症级")
    private Integer intensiveLevel;

    @ApiModelProperty(value = "报告结果")
    private String projectsResult;

    @ApiModelProperty(value = "是否正常")
    private Integer isNormal;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "报告结果上限")
    private Integer resultUpper;

    @ApiModelProperty(value = "报告结果下限")
    private Integer resultFloor;

    @ApiModelProperty(value = "检验医师")
    private String doctorId;

    @ApiModelProperty(value = "检查医生代码")
    private String inspectCode;

    @ApiModelProperty(value = "检验时间")
    private Date inspectTime;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;
}
