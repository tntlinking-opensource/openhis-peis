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
 * KS问诊——症状(MdWzSymptom)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
@Data
@TableName("md_wz_symptom")
@ApiModel(value = "MdWzSymptom", description = "KS问诊——症状实体类")
public class MdWzSymptom extends Model<MdWzSymptom> implements Serializable {
    private static final long serialVersionUID = -83417976411208255L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "症状ID")
    private String symptomId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}
