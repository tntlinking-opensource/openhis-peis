package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * jc禁忌疾病分类(MdDrugDiseaseType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:14
 */
@Data
@TableName("md_drug_disease_type")
@ApiModel(value = "MdDrugDiseaseType", description = "jc禁忌疾病分类实体类")
public class MdDrugDiseaseType extends Model<MdDrugDiseaseType> implements Serializable {
    private static final long serialVersionUID = 277508969955645892L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "禁忌疾病分类名称")
    private String healthEvaluation;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
