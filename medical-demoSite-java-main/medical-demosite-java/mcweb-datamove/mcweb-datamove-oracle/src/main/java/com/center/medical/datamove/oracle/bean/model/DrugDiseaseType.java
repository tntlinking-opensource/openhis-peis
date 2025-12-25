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
 * jc禁忌疾病分类(DrugDiseaseType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:26
 */
@Data
@TableName("DRUG_DISEASE_TYPE")
@ApiModel(value = "DrugDiseaseType", description = "jc禁忌疾病分类实体类")
public class DrugDiseaseType extends Model<DrugDiseaseType> implements Serializable {
    private static final long serialVersionUID = 703427758954761174L;

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
