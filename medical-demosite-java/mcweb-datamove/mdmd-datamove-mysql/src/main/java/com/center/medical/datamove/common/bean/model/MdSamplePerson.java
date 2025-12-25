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
 * TJ团检报告人员样本表(MdSamplePerson)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:26
 */
@Data
@TableName("md_sample_person")
@ApiModel(value = "MdSamplePerson", description = "TJ团检报告人员样本表实体类")
public class MdSamplePerson extends Model<MdSamplePerson> implements Serializable {
    private static final long serialVersionUID = 336755271619775295L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "团检报告ID")
    private String ballCheckId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "体检者表id")
    private String patientId;
}
